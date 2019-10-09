package com.lind.basic.util;

import java.util.function.Consumer;
import java.util.function.Predicate;
import org.junit.Test;

public class FunctionInterfaceTest {
  private int testValue = 0;


  @Test
  public void testlambdaFunction() {
    java8Fun("类似.net里的委托", message -> System.out.println(message));
  }

  @Test
  public void testMethodFunction() {
    java8Fun("类似.net里的委托", new Log() {
      @Override
      public void info(String message) {
        System.out.println(message);
      }
    });
  }

  @Test
  public void variable() {
    printDefault();
  }

  void printDefault() {
    int b = 0;
    System.out.println(String.format("testValue=%d,b=%d", testValue, b));
  }

  @Test
  public void staticMethod() {
    StaticMethod.helloWorld();
  }

  @Test
  public void lambda() {
    logger(i -> System.out.println(i), "hello");
  }

  @Test
  public void evenTest() {
    evenFun(i -> i % 2 == 0, 1);
  }

  private void logger(Consumer<String> action, String msg) {
    action.accept(msg);
  }

  private String evenFun(Predicate<Integer> even, Integer val) {
    if (even.test(val)) {
      return "偶数";
    }
    return "奇数";
  }

  public void java8Fun(String message, Log run) {
    System.out.println("执行java8函数式接口");
    run.info(message);
  }

  @FunctionalInterface
  interface Log {
    void info(String message);
  }

  interface StaticMethod {
    static void helloWorld() {
      System.out.println("hello world!");
    }
  }
}
