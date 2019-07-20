package com.lind.basic.encryption;

import com.lind.basic.util.EncryptionUtils;
import org.junit.Test;

public class Md5Test {
  @Test
  public void md5_32() {
    System.out.println(EncryptionUtils.MD5("hello"));
  }
}
