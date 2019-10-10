package com.lind.basic.algorithm;

import org.junit.Test;

public class Fibonacci {
  /**
   * 求斐波那契数列的第n项，n从0开始
   *
   * @param n 第n项
   * @return 第n项的值
   */
  public int Fibonacci(int n) {
    if (n < 2) {
      return n;
    }

    int a = 1, b = 1;
    for (int i = 2; i < n; ++i) {
      b = a + b;
      a = b - a;
    }
    return b;
  }

  /**
   * 青蛙跳台阶
   *
   * @param target 跳上的那一级台阶
   * @return 多少种跳法
   */
  public int JumpFloor(int target) {
    if (target < 3) {
      return target;
    }
    int a = 1, b = 2;
    for (int i = 3; i <= target; i++) {
      b = a + b;
      a = b - a;
    }
    return b;
  }

  /**
   * 求斐波那契数列的第n项，n从0开始
   *
   * @param n 第n项
   * @return 第n项的值
   */
  public int FibonacciRec(int n) {
    if (n < 2) {
      return n;
    }
    return Fibonacci(n - 1) + Fibonacci(n - 2);
  }

  /**
   * 青蛙跳台阶II
   *
   * @param target 跳上的那一级台阶
   * @return 多少种跳法
   */
  public int JumpFloorII(int target) {
    return (int) Math.pow(2, target - 1);
  }

  @Test
  public void rabbit() {
    int level = 8;
    System.out.printf("%d level = %d", level, FibonacciRec(level));
  }

  @Test
  public void jump() {
    int level = 8;
    System.out.printf("%d level = %d", level, JumpFloor(level));
  }
}
