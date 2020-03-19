package com.lind.basic.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordTest {
  private static final String SITE_WIDE_SECRET = "my-secret-key";
  private static final PasswordEncoder passwordEncoder = new StandardPasswordEncoder(
      SITE_WIDE_SECRET);

  @Test
  public void passwordEncoderTest() {
    String old = passwordEncoder.encode("abc1234");
    Assert.assertTrue(passwordEncoder.matches("abc1234", old));
  }

  @Test
  public void cryptPasswordEncoderTest() {
    System.out.println("pass8:" + new BCryptPasswordEncoder(8).encode("12345"));

    System.out.println("pass4:" + new BCryptPasswordEncoder(4).encode("12345"));
  }
}
