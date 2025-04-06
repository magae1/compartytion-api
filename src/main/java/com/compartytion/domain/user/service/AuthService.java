package com.compartytion.domain.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.compartytion.domain.model.ForgivenPassword;
import com.compartytion.domain.model.UnauthenticatedEmail;
import com.compartytion.domain.model.entity.Account;
import com.compartytion.domain.repository.AccountRepository;
import com.compartytion.domain.repository.ForgivenPasswordRepository;
import com.compartytion.domain.repository.UnauthenticatedEmailRepository;
import com.compartytion.domain.user.dto.response.EmailExistenceResponse;
import com.compartytion.domain.user.dto.request.EmailOTPRequest;
import com.compartytion.domain.user.dto.response.EmailOTPResponse;
import com.compartytion.domain.user.dto.request.PasswordChangeRequest;
import com.compartytion.domain.user.dto.request.SignUpRequest;
import com.compartytion.domain.user.mapper.AccountMapper;
import com.compartytion.global.component.OTPGenerator;
import com.compartytion.global.component.EmailSender;
import com.compartytion.global.utils.PasswordValidator;

import static com.compartytion.domain.user.enums.AuthFormExceptions.NOT_FOUND_EMAIL;
import static com.compartytion.domain.user.enums.AuthFormExceptions.ALREADY_VERIFIED_EMAIL;
import static com.compartytion.domain.user.enums.AuthFormExceptions.DUPLICATED_EMAIL;
import static com.compartytion.domain.user.enums.AuthFormExceptions.NOT_FOUND_FORGIVEN_PASSWORD;
import static com.compartytion.domain.user.enums.AuthFormExceptions.NOT_FOUND_UNAUTHENTICATED_EMAIL;
import static com.compartytion.domain.user.enums.AuthFormExceptions.NOT_VERIFIED_EMAIL;
import static com.compartytion.domain.user.enums.AuthFormExceptions.WRONG_OTP;


@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {

  private final AccountRepository accountRepo;
  private final UnauthenticatedEmailRepository unauthenticatedEmailRepo;
  private final ForgivenPasswordRepository forgivenPasswordRepo;
  private final EmailSender emailSender;
  private final PasswordEncoder passwordEncoder;
  private final OTPGenerator otpGenerator;

  @Value("${otp.ttl}")
  private long otpTtl;


  private boolean existsByEmail(String email) {
    return accountRepo.existsByEmail(email);
  }

  private void insertUnauthenticatedEmail(String email, String otp) {
    unauthenticatedEmailRepo.save(UnauthenticatedEmail.builder()
        .email(email)
        .otp(otp)
        .ttl(otpTtl)
        .build());
  }

  private void insertForgivenPassword(String email, String otp) {
    forgivenPasswordRepo.save(ForgivenPassword.builder()
        .email(email)
        .otp(otp)
        .ttl(otpTtl)
        .build());
  }

  private void updateUnauthenticatedEmailVerified(String email) {
    unauthenticatedEmailRepo.save(UnauthenticatedEmail.builder()
        .email(email)
        .isVerified(true)
        .ttl(otpTtl * 100)
        .build()
    );
  }

  private void updateForgivenPasswordVerified(String email) {
    forgivenPasswordRepo.save(ForgivenPassword.builder()
        .email(email)
        .isVerified(true)
        .ttl(otpTtl * 100)
        .build());
  }

  private void sendOTPByEmail(String email, String otp) {
    emailSender.sendMail(email, "인증을 위한 OTP를 보내드립니다.", otp);
  }

  /**
   * 이메일 존재 여부를 확인합니다.
   *
   * @param email 존재하는지 확인할 이메일
   * @return {@link EmailExistenceResponse}
   */
  public EmailExistenceResponse checkEmailExistence(String email) {
    boolean exists = existsByEmail(email);
    return new EmailExistenceResponse(email, exists);
  }

  /**
   * 회원가입을 위한 OTP를 전송합니다.
   *
   * @param email OTP를 수신할 이메일
   * @return {@link EmailOTPResponse}
   * @throws RuntimeException OTP 전송 실패 시 발생
   */
  public EmailOTPResponse sendOTPForSignup(String email) throws RuntimeException {
    log.debug("email: {}", email);
    if (existsByEmail(email)) {
      throw DUPLICATED_EMAIL.toInvalidFormException();
    }
    log.info("Email {} accepted!", email);

    String otp = otpGenerator.next();
    insertUnauthenticatedEmail(email, otp);
    sendOTPByEmail(email, otp);
    return new EmailOTPResponse(email, otpTtl);
  }

  /**
   * 회원가입을 위한 OTP 인증을 시도합니다.
   *
   * @param request {@link EmailOTPRequest}
   * @throws RuntimeException OTP 인증 실패 시 발생
   */
  public void verifyOTPForSignup(EmailOTPRequest request) throws RuntimeException {
    UnauthenticatedEmail unauthenticatedEmail = unauthenticatedEmailRepo.findById(request.email())
        .orElseThrow(NOT_FOUND_UNAUTHENTICATED_EMAIL::toInvalidFormException);

    if (unauthenticatedEmail.isVerified()) {
      throw ALREADY_VERIFIED_EMAIL.toInvalidFormException();
    }

    if (!unauthenticatedEmail.getOtp().equals(request.otp())) {
      throw WRONG_OTP.toInvalidFormException();
    }

    updateUnauthenticatedEmailVerified(request.email());
  }

  /**
   * 비밀번호 변경을 위한 OTP를 전송합니다.
   *
   * @param email OTP를 수신할 이메일
   * @return {@link EmailOTPResponse}
   * @throws RuntimeException OTP 발송 실패 시 발생
   */
  public EmailOTPResponse sendOTPForChangePassword(String email) throws RuntimeException {
    if (!existsByEmail(email)) {
      throw NOT_FOUND_EMAIL.toInvalidFormException();
    }

    String otp = otpGenerator.next();
    insertForgivenPassword(email, otp);
    sendOTPByEmail(email, otp);
    return new EmailOTPResponse(email, otpTtl);
  }

  /**
   * 비밀번호 변경을 위한 OTP 인증을 시도합니다.
   *
   * @param request {@link EmailOTPRequest}
   * @throws RuntimeException 인증 실패 시 발생
   */
  public void verifyOTPForChangePassword(EmailOTPRequest request) throws RuntimeException {
    ForgivenPassword forgivenPassword = forgivenPasswordRepo.findById(request.email())
        .orElseThrow(NOT_FOUND_FORGIVEN_PASSWORD::toInvalidFormException);

    if (forgivenPassword.isVerified()) {
      throw ALREADY_VERIFIED_EMAIL.toInvalidFormException();
    }

    if (!forgivenPassword.getOtp().equals(request.otp())) {
      throw WRONG_OTP.toInvalidFormException();
    }

    updateForgivenPasswordVerified(request.email());
  }

  /**
   * 비밀번호 변경을 시도합니다.
   *
   * @param email   비밀번호를 변경하고자 하는 계정의 이메일
   * @param request {@link PasswordChangeRequest}
   * @throws RuntimeException 비밀번호 변경 실패 시 발생
   */
  public void changePassword(String email, PasswordChangeRequest request)
      throws RuntimeException {
    PasswordValidator.validate(request.password(), request.confirmedPassword());

    ForgivenPassword forgivenPassword = forgivenPasswordRepo.findById(email)
        .orElseThrow(NOT_FOUND_FORGIVEN_PASSWORD::toInvalidFormException);

    if (!forgivenPassword.isVerified()) {
      throw NOT_VERIFIED_EMAIL.toInvalidFormException();
    }

    Account account = accountRepo.findByEmail(email)
        .orElseThrow(NOT_FOUND_EMAIL::toInvalidFormException);
    account.changePassword(request.password(), passwordEncoder);
    accountRepo.save(account);
  }

  /**
   * 회원가입을 시도합니다.
   *
   * @param request {@link SignUpRequest}
   * @throws RuntimeException 회원가입 실패 시 발생
   */
  public void signUp(SignUpRequest request) throws RuntimeException {
    PasswordValidator.validate(request.password(), request.confirmedPassword());

    UnauthenticatedEmail unauthenticatedEmail = unauthenticatedEmailRepo.findById(request.email())
        .orElseThrow(NOT_FOUND_UNAUTHENTICATED_EMAIL::toInvalidFormException);

    if (!unauthenticatedEmail.isVerified()) {
      throw NOT_VERIFIED_EMAIL.toInvalidFormException();
    }

    if (existsByEmail(request.email())) {
      unauthenticatedEmailRepo.deleteById(request.email());
      throw DUPLICATED_EMAIL.toInvalidFormException();
    }

    log.info("Sign up with email {}", request.email());
    Account account = AccountMapper.toEntity(request, passwordEncoder);
    accountRepo.save(account);
    unauthenticatedEmailRepo.deleteById(request.email());
  }

}
