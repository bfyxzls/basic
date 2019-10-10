package com.lind.basic.encryption;

import com.lind.basic.util.EncryptionUtils;
import org.junit.Test;

public class Base64Test {
  @Test
  public void encode() throws Exception {
    System.out.println(
        EncryptionUtils.encryptBASE64("hello".getBytes()));
  }

  @Test
  public void decode() throws Exception {
    System.out.println(new String(
        EncryptionUtils.decryptBASE64(
            EncryptionUtils.encryptBASE64("hello".getBytes()))));
  }
}
