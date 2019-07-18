package com.lind.basic.grammar;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DesignModelTest {

  @Test
  public void authServiceTest() {
    AuthService authService = new AuthService();
    authService.configCode("123456").configToken("abcdefg");
    System.out.println(authService.toString());
  }

  class AuthService {
    String token;
    String code;

    public AuthService configToken(String token) {
      this.token = token;
      return this;
    }

    public AuthService configCode(String code) {
      this.code = code;
      return this;
    }

    @Override
    public String toString() {
      return "token=" + token + ",code=" + code;
    }
  }
}
