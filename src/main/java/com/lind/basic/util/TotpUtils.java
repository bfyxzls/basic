package com.lind.basic.util;

import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * TOTP算法工具类.
 */

public class TotpUtils {

  private static final int[] DIGITS_POWER
      // 0   1  2     3    4       5      6       7        8
      = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};

  private TotpUtils() {
    throw new AssertionError();
  }

  /**
   * This method uses the JCE to provide the crypto algorithm.
   * HMAC computes a Hashed Message Authentication Code with the
   * crypto hash algorithm as a parameter.
   */
  private static byte[] hmac_sha(String crypto, byte[] keyBytes,
                                 byte[] text) {
    try {
      Mac hmac;
      hmac = Mac.getInstance(crypto);
      SecretKeySpec macKey =
          new SecretKeySpec(keyBytes, "RAW");
      hmac.init(macKey);
      return hmac.doFinal(text);
    } catch (GeneralSecurityException gse) {
      throw new UndeclaredThrowableException(gse);
    }
  }

  /**
   * This method converts a HEX string to Byte[].
   */
  public static byte[] hexStr2Bytes(String hex) {
    // Adding one byte to get the right conversion
    // Values starting with "0" can be converted
    byte[] bitArray = new BigInteger("10" + hex, 16).toByteArray();

    // Copy all the REAL bytes, not the "first"
    byte[] ret = new byte[bitArray.length - 1];
    for (int i = 0; i < ret.length; i++) {
      ret[i] = bitArray[i + 1];
    }
    return ret;
  }

  /**
   * This method generates a TOTP value for the given.
   */
  public static String generateTotp(String key,
                                    String time,
                                    String returnDigits) {
    return generateTotp(key, time, returnDigits, "HmacSHA1");
  }

  /**
   * This method generates a TOTP value for the given.
   */
  public static String generateTotp(String key,
                                    String time,
                                    String returnDigits,
                                    String crypto) {
    int codeDigits = Integer.decode(returnDigits).intValue();
    String result = null;

    // Using the counter
    // First 8 bytes are for the movingFactor
    // Compliant with base RFC 4226 (HOTP)
    while (time.length() < 16) {
      time = "0" + time;
    }

    // Get the HEX in a Byte[]
    byte[] msg = hexStr2Bytes(time);
    byte[] k = hexStr2Bytes(key);
    byte[] hash = hmac_sha(crypto, k, msg);

    // put selected bytes into result int
    int offset = hash[hash.length - 1] & 0xf;

    int binary =
        ((hash[offset] & 0x7f) << 24)
            |
            ((hash[offset + 1] & 0xff) << 16)
            |
            ((hash[offset + 2] & 0xff) << 8)
            |
            (hash[offset + 3] & 0xff);

    int otp = binary % DIGITS_POWER[codeDigits];

    result = Integer.toString(otp);
    while (result.length() < codeDigits) {
      result = "0" + result;
    }
    return result;
  }

  /**
   * This method generates a TOTP value for the given.
   */
  public static String generateTotp256(String key,
                                       String time,
                                       String returnDigits) {
    return generateTotp(key, time, returnDigits, "HmacSHA256");
  }


  /**
   * This method generates a TOTP value for the given.
   */
  public static String generateTOTP512(String key,
                                       String time,
                                       String returnDigits) {
    return generateTotp(key, time, returnDigits, "HmacSHA512");
  }

}