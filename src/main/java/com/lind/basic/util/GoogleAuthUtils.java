package com.lind.basic.util;

import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

public class GoogleAuthUtils {

  /**
   * Suppress default constructor for noninstantiability.
   */
  private GoogleAuthUtils() {
    throw new AssertionError();
  }


  /**
   * 随机生成一个密钥.
   * https://www.jianshu.com/p/de903c074d77
   */
  public static String createSecretKey() {
    SecureRandom random = new SecureRandom();
    byte[] bytes = new byte[20];
    random.nextBytes(bytes);
    Base32 base32 = new Base32();
    String secretKey = base32.encodeToString(bytes);
    return secretKey.toLowerCase();
  }

  /**
   * 根据密钥获取验证码.
   * 返回字符串是因为验证码有可能以 0 开头
   *
   * @param secretKey 密钥
   * @param time      第几个 30 秒 System.currentTimeMillis() / 1000 / 30
   */
  public static String getTotpString(String secretKey, long time) {
    Base32 base32 = new Base32();
    byte[] bytes = base32.decode(secretKey.toUpperCase());
    String hexKey = Hex.encodeHexString(bytes);
    String hexTime = Long.toHexString(time);
    return TotpUtils.generateTotp(hexKey, hexTime, "6");
  }

}
