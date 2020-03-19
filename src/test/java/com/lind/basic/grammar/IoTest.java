package com.lind.basic.grammar;

import org.junit.Assert;
import org.junit.Test;

public class IoTest {

  @Test
  public void memoryTest() {
    System.out.println("freeMemory:" + Runtime.getRuntime().freeMemory());
    System.out.println("totalMemory:" + Runtime.getRuntime().totalMemory());
    Assert.assertNotSame(Runtime.getRuntime().totalMemory(), Runtime.getRuntime().freeMemory());
  }
}
