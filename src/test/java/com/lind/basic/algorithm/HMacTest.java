package com.lind.basic.algorithm;

import com.lind.basic.util.HmacUtils;
import org.junit.Test;

/**
 * 关于hmac算法测试.
 */
public class HMacTest {
  @Test
  public void hmac() throws Exception {
    byte[] pwd = HmacUtils.hmacsha1Encrypt("beijing", "zzl123");
    for (byte i : pwd) {
      System.out.println(i);
    }
  }


  @Test
  public void randomFromNum() {
    int num = (int) Math.pow(10, 6);
    int otp = 1003001 % num;
    String result = Integer.toString(otp);
    while (result.length() < 6) {
      result = "0" + result;
    }
    System.out.println("result=" + result);
  }

  @Test
  public void offset() {
    System.out.println(20 & 0xf);//0xf:15
    System.out.println(200 & 0x7f);//0xf:127
    System.out.println(300 & 0xff);//0xf:255
  }

  @Test
  public void division() {
    int seed = 30;
    int num = 100;
    int[] a = {num, num + 6, num + 1, num + 2, num + 3, num + 4, num - 1};

    for (int item : a) {
      System.out.println("item=" + item + ",result=" + item / seed);
    }

  }

}
