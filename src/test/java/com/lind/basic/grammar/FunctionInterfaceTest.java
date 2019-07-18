package com.lind.basic.grammar;

import java.util.function.Consumer;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
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
  public void printConsumerTest() {
    logger("hello", i -> System.out.println(i));
  }

  @Test
  public void logConsumerTest() {
    System.out.println("logConsumerTest方法");
    logger("hello", i -> logger.info(i));
  }

  @Test
  public void evenPredicateTest() {
    evenFun(2, i -> i % 2 == 0);
  }

  /**
   * 没有返回值的委托.
   *
   * @param action .
   * @param msg    .
   */
  private void logger(String msg, Consumer<String> action) {
    System.out.println("logger执行开始，方法的逻辑，这个方法是公用的,action委托是动态的");
    //执行个性化的action方法
    action.accept(msg);
    System.out.println("logger执行结束");
  }

  /**
   * 带有返回值的委托.
   *
   * @param even .
   * @param val  .
   * @return
   */
  private String evenFun(Integer val, Predicate<Integer> even) {
    if (even.test(val)) {
      return "偶数";
    }
    return "奇数";
  }

  /**
   * 函数式接口.
   *
   * @param message .
   * @param run     委托方法
   */
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
