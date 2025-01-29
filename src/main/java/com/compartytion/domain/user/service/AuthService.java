package com.compartytion.domain.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.compartytion.domain.entity.Account;
import com.compartytion.domain.model.UnauthenticatedEmail;
import com.compartytion.domain.user.dto.EmailExistenceResponse;
import com.compartytion.domain.user.dto.SignUpRequest;
import com.compartytion.domain.user.mapper.AccountMapper;
import com.compartytion.domain.user.repository.AccountRepository;
import com.compartytion.domain.user.repository.UnauthenticatedEmailRepository;
import com.compartytion.domain.user.utils.OTPGenerator;
import com.compartytion.global.component.EmailSender;

import static com.compartytion.domain.user.exception.AuthExceptions.DUPLICATED_EMAIL;
import static com.compartytion.domain.user.exception.AuthExceptions.NOT_FOUND_UNAUTHENTICATED_EMAIL;
import static com.compartytion.domain.user.exception.AuthExceptions.NOT_MATCHED_PASSWORD;
import static com.compartytion.domain.user.exception.AuthExceptions.NOT_VERIFIED_EMAIL;


@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {

  private final AccountRepository accountRepo;
  private final UnauthenticatedEmailRepository unauthenticatedEmailRepo;
  private final EmailSender emailSender;
  private final PasswordEncoder passwordEncoder;

  @Value("${otp.expire-interval}")
  private Long otpExpireInterval;


  private boolean existsByEmail(String email) {
    return accountRepo.existsByEmail(email);
  }

  public EmailExistenceResponse checkEmailExistence(String email) {
    boolean exists = existsByEmail(email);
    return new EmailExistenceResponse(email, exists);
  }

  public void sendOTPByEmail(String email) throws ResponseStatusException {
    if (existsByEmail(email)) {
      throw DUPLICATED_EMAIL.toResponseStatusException();
    }
    log.info("Email {} accepted!", email);

    String otp = OTPGenerator.generateOTP();
    unauthenticatedEmailRepo.save(UnauthenticatedEmail.builder()
        .email(email)
        .otp(otp)
        .expiration(otpExpireInterval)
        .build());
    emailSender.sendMail(email, "OTP", otp);
  }

  public void signUp(SignUpRequest signUpRequest) throws ResponseStatusException {
    String requestedEmail = signUpRequest.email();

    UnauthenticatedEmail unauthenticatedEmail = unauthenticatedEmailRepo.findById(requestedEmail)
        .orElseThrow(NOT_FOUND_UNAUTHENTICATED_EMAIL::toResponseStatusException);

    if (!unauthenticatedEmail.isVerified()) {
      throw NOT_VERIFIED_EMAIL.toResponseStatusException();
    }

    if (existsByEmail(requestedEmail)) {
      unauthenticatedEmailRepo.delete(unauthenticatedEmail);
      throw DUPLICATED_EMAIL.toResponseStatusException();
    }

    String password = signUpRequest.password();
    String confirmedPassword = signUpRequest.confirmedPassword();
    if (!password.equals(confirmedPassword)) {
      throw NOT_MATCHED_PASSWORD.toResponseStatusException();
    }

    log.info("Sign up with email {}", requestedEmail);
    String encryptedPassword = passwordEncoder.encode(password);
    Account account = AccountMapper.toEntity(signUpRequest, encryptedPassword);
    accountRepo.save(account);
  }

}
