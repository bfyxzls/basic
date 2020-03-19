package com.lind.basic.grammar;

import org.junit.Assert;
import org.junit.Test;

public class MathTest {
  @Test
  public void max() {
    Assert.assertEquals(20, Math.max(1, 20));
  }

  @Test
  public void min() {
    Assert.assertEquals(1, Math.min(1, 10));
  }

  /**
   * 位运算的速度比其它整型要快的多，左移1位乘以2，右移1位除以2.
   */
  @Test
  public void bitMove() {
    Assert.assertEquals(2, 16 >> 3); // 16/2^3=2
    Assert.assertEquals(4, 2 << 1);
    Assert.assertEquals(2, 2 << 0);
    Assert.assertEquals(2, 2 >> 0);

  }

  @Test
  public void multiBitMove() {
    System.out.println(9 >>> 1); // 无符号右移，忽略符号位，空位都以0补齐
    System.out.println(9 >>> 2);
    System.out.println(9 >>> 3);

  }

  @Test
  public void multiBitMove2() {
    System.out.println(5 >> 0);
  }

  @Test
  public void logarithm() {
    System.out.println(
        Math.log10(100));
  }

  @Test
  public void logarithm2() {
    System.out.println(
        Math.log(Math.E));
  }
}
