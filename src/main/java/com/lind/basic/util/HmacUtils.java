package com.lind.basic.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HmacUtils {

  /**
   * Suppress default constructor for noninstantiability.
   */
  private HmacUtils() {
    throw new AssertionError();
  }

  private static final String MAC_NAME = "HmacSHA1";
  private static final String ENCODING = "UTF-8";

  /**
   * 使用 HMAC-SHA1 签名方法对对encryptText进行签名.
   *
   * @param encryptText 被签名的字符串
   * @param encryptKey  密钥
   * @return
   */
  public static byte[] hmacsha1Encrypt(String encryptText, String encryptKey)
      throws Exception {

    byte[] data = encryptKey.getBytes(ENCODING);
    //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
    SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
    //生成一个指定 Mac 算法 的 Mac 对象
    Mac mac = Mac.getInstance(MAC_NAME);
    //用给定密钥初始化 Mac 对象
    mac.init(secretKey);

    byte[] text = encryptText.getBytes(ENCODING);
    //完成 Mac 操作
    return mac.doFinal(text);
  }
}
