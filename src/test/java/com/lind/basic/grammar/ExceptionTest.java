package com.lind.basic.grammar;

import org.junit.Test;

public class ExceptionTest {
  @Test(expected = ArithmeticException.class)
  public void innerExceptionTest() {
    try {
      int a = 0;
      int b = 1 / a;
    } catch (Exception ex) {
      throw ex;
    } finally {
      System.out.println("success end.");
    }
  }

  @Test()
  public void throwTest() {
    try {
      int a = 0;
      int b = 1 / a;
    } catch (Exception ex) {
      ex.printStackTrace();//只看控制台输入堆栈信息并没有throw
    }
  }
}
