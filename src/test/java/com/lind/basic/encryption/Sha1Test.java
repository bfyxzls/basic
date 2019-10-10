package com.lind.basic.encryption;

import com.lind.basic.util.EncryptionUtils;
import com.lind.basic.util.HmacUtils;
import org.junit.Test;

public class Sha1Test {
  @Test
  public void sha1() {
    System.out.println(EncryptionUtils.sha("hello"));
  }

  @Test
  public void hmac() throws Exception {
    byte[] pwd = HmacUtils.hmacsha1Encrypt("hello", "lind");
    String message = new String(pwd);
    System.out.println(message);

    byte[] hello = "hello".getBytes();
    System.out.println(new String(hello));
  }
}
