package com.lind.basic.jvm;

import org.junit.Test;

public class StackTest {
  private static long count = 0;

  public static void infinitelyRecursiveMethod(long a) {
    System.out.println(count++);
    if (count < 100) {
      infinitelyRecursiveMethod(a);
    }
  }

  @Test
  public void xss() {
    infinitelyRecursiveMethod(1);

  }
}
