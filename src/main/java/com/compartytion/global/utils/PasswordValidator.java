package com.compartytion.global.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.compartytion.global.exception.InvalidFormException;

public class PasswordValidator {

  private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";

  private static final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

  private static final String[] MSG = {"비밀번호는 최소 8자 이상이어야 합니다.", "알파벳 소문자가 하나 이상 포함되어야 합니다.",
      "알파벳 대문자가 하나 이상 포함되어야 합니다.", "숫자가 하나 이상 포함되어야 합니다."};

  public static boolean isValid(String password) {
    return pattern.matcher(password).matches();
  }

  public static void validate(String password, String confirmedPassword)
      throws InvalidFormException {
    Map<String, List<String>> msgMap = new HashMap<>();
    if (password == null || password.isBlank()) {
      msgMap.put("password", List.of("비밀번호는 반드시 입력헤야 합니다."));
    } else {
      if (!PasswordValidator.isValid(password)) {
        msgMap.put("password", List.of(PasswordValidator.getMessages()));
      }
    }

    if (confirmedPassword == null || confirmedPassword.isBlank()) {
      msgMap.put("confirmedPassword", List.of("비밀번호(확인)은 반드시 입력해야 합니다."));
    } else {
      if (!confirmedPassword.equals(password)) {
        msgMap.put("confirmedPassword", List.of("비밀번호와 비밀번호(확인)이 일치하지 않습니다."));
      }
    }

    if (!msgMap.isEmpty()) {
      throw new InvalidFormException(msgMap);
    }
  }

  public static String[] getMessages() {
    return MSG;
  }
}
