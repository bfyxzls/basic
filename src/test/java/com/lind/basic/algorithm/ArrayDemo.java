package com.lind.basic.algorithm;

import org.junit.Assert;
import org.junit.Test;

public class ArrayDemo {

  /**
   * 数组中重复的数据.
   */
  @Test
  public void addTest() {
    Assert.assertEquals(20, add(15,  5));
  }

  /**
   * 不用加减乘除做加法
   *
   * @param num1 数1
   * @param num2 数2
   * @return 两数之和
   */
  public int add(int num1, int num2) {
    int sum, carry;
    while (true) {
      sum = num1 ^ num2;
      carry = (num1 & num2) << 1;
      num1 = sum;
      num2 = carry;
      if (num2 == 0) {
        break;
      }
    }
    return num1;
  }

}
