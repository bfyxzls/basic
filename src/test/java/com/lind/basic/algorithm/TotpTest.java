package com.lind.basic.algorithm;

import com.lind.basic.util.GoogleAuthUtils;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

public class TotpTest {
  @Test
  public void googleAuth() throws Exception {
    String key = GoogleAuthUtils.createSecretKey();
    System.out.println("key=" + key);

    long time = System.currentTimeMillis() / 1000 / 4;
    String num = GoogleAuthUtils.getTotpString(key, time);
    System.out.println("num1=" + num);
    Thread.sleep(1000);

    time = System.currentTimeMillis() / 1000 / 4;
    String num2 = GoogleAuthUtils.getTotpString(key, time);
    System.out.println("num2=" + num2);
    Thread.sleep(5000);

    time = System.currentTimeMillis() / 1000 / 4;
    String num3 = GoogleAuthUtils.getTotpString(key, time);
    System.out.println("num3=" + num3);
  }

  @Test
  public void offset_byte() throws Exception {
    /**
     * 5.4.  Example of HOTP Computation for Digit = 6.
     *    The following code example describes the extraction of a dynamic
     *    binary code given that hmac_result is a byte array with the HMAC-
     *    sha-1 result:
     *         int offset   =  hmac_result[19] & 0xf ;
     *         int bin_code = (hmac_result[offset]  & 0x7f) << 24
     *            | (hmac_result[offset+1] & 0xff) << 16
     *            | (hmac_result[offset+2] & 0xff) <<  8
     *            | (hmac_result[offset+3] & 0xff) ;
     *    sha-1 HMAC Bytes (Example)
     *    -------------------------------------------------------------
     *    | Byte Number                                               |
     *    -------------------------------------------------------------
     *    |00|01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16|17|18|19|
     *    -------------------------------------------------------------
     *    | Byte Value                                                |
     *    -------------------------------------------------------------
     *    |1f|86|98|69|0e|02|ca|16|61|85|50|ef|7f|19|da|8e|94|5b|55|5a|
     *    -------------------------------***********----------------++|
     * M'Raihi, et al.              Informational                      [Page 7]
     * RFC 4226                     HOTP Algorithm                December 2005
     *    * The last byte (byte 19) has the hex value 0x5a.
     *    * The value of the lower 4 bits is 0xa (the offset value).
     *    * The offset value is byte 10 (0xa).
     *    * The value of the 4 bytes starting at byte 10 is 0x50ef7f19,
     *      which is the dynamic binary code DBC1.
     *    * The MSB of DBC1 is 0x50 so DBC2 = DBC1 = 0x50ef7f19 .
     *    * HOTP = DBC2 modulo 10^6 = 872921.
     *    We treat the dynamic binary code as a 31-bit, unsigned, big-endian
     *    integer; the first byte is masked with a 0x7f.
     *    We then take this number modulo 1,000,000 (10^6) to generate the 6-
     *    digit HOTP value 872921 decimal.
     */
    String input = "hello";
    byte[] hmacResult = DigestUtils.sha1(input);
    int offset = hmacResult[19] & 0xf;
    int binCode = (hmacResult[offset] & 0x7f) << 24
        | (hmacResult[offset + 1] & 0xff) << 16
        | (hmacResult[offset + 2] & 0xff) << 8
        | (hmacResult[offset + 3] & 0xff);
    int mod = binCode % 1000000;
    System.out.println(mod);
  }

  /**
   * 时间戳相差30秒内，它们整除30的结果相同.
   */
  @Test
  public void totp_time() {
    System.out.println("10的16进制表示为：" + Long.toHexString(Long.valueOf(10)));
    long timer = System.currentTimeMillis();

    long a1 = timer / 1000 / 30;
    System.out.println("a1=" + a1 + ",toHexString=" + Long.toHexString(a1));

    long a2 = (timer + 10000) / 1000 / 30; //落后1秒，看结果是否相同
    System.out.println("落后2秒");
    System.out.println("a2=" + a2 + ",toHexString=" + Long.toHexString(a2));


    long a4 = (timer + 20000) / 1000 / 30; //落后1秒，看结果是否相同
    System.out.println("落后20秒");
    System.out.println("a4=" + a4 + ",toHexString=" + Long.toHexString(a4));

    long a3 = (timer + 50000) / 1000 / 30; //落后1秒，看结果是否相同
    System.out.println("落后50秒");
    System.out.println("a3=" + a3 + ",toHexString=" + Long.toHexString(a3));

  }

  @Test
  public void hex_one_byte_capacity() {
    int a1 = 0xf;
    System.out.println(a1);
    Assert.assertEquals(0x0f, 0xf);
    byte[] arr = new byte[4];
    arr[0] = 0x0f;
    arr[1] = 0x1f;
    arr[2] = 0x3f;
    arr[3] = 0x7f;
    for (byte i : arr) {
      System.out.println(i);

    }
    int sum = arr[0] << 24 | arr[1] << 16 | arr[2] << 8 | arr[3];
    sum = sum % 1000000;
    System.out.println(sum);
  }

  @Test
  public void hexString() {
    String key = "hello";
    Base32 base32 = new Base32();
    byte[] bytes = base32.decode(key.toUpperCase());//大写的base32编码
    String hexKey = Hex.encodeHexString(bytes);//传为16位的字符
    System.out.println(hexKey);
    String hexTime = Long.toHexString(System.currentTimeMillis() / 1000);
    System.out.println(hexTime);
  }
}
