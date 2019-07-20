package com.lind.basic.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptionUtils {
  /**
   * BASE64解密.
   *
   * @param key .
   * @return
   */
  public static byte[] decryptBASE64(String key) throws Exception {
    return (new BASE64Decoder()).decodeBuffer(key);
  }

  /**
   * BASE64加密.
   *
   * @param key .
   * @return
   */
  public static String encryptBASE64(byte[] key) throws Exception {
    return (new BASE64Encoder()).encodeBuffer(key);
  }

  /**
   * MD5加载.
   *
   * @param inputStr 明文
   * @param len      长度
   * @return
   */
  public static String MD5(String inputStr, int len) {
    BigInteger bigInteger = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] inputData = inputStr.getBytes();
      md.update(inputData);
      bigInteger = new BigInteger(md.digest());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bigInteger.toString(len);
  }

  /**
   * 长度为32位的MD5字符.
   *
   * @param inputStr 明文
   * @return
   */
  public static String MD5(String inputStr) {
    return MD5(inputStr, 32);
  }

  /**
   * SHA1的加密算法.
   *
   * @param inputStr 明文
   * @return
   */
  public static String SHA(String inputStr) {
    BigInteger sha = null;
    byte[] inputData = inputStr.getBytes();
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA");
      messageDigest.update(inputData);
      sha = new BigInteger(messageDigest.digest());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sha.toString(32);
  }
}
