package com.lind.basic.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class BasicGrammarTest {

  @Test
  public void interfaceAbstract() {

    Do do1 = () -> System.out.println("Hello");
    Do do2 = new Do() {
      @Override
      public void print() {
        System.out.println("OK");
      }
    };
    do1.print();
    do1.description();
    do2.description();
  }

  @Test
  public void java8_before_anonymous() {
    // create anonymous inner class object
    new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("New thread created");
      }
    }).start();
  }

  @Test
  public void java8_lambda() {
    // lambda expression to create the object
    new Thread(() -> {
      System.out.println("New thread created");
    }).start();
  }

  @Test
  public void lambda() {
    List<String> names = Arrays.asList("Geek", "GeeksQuiz", "g1", "QA", "Geek2");
    Predicate<String> p = (s) -> s.startsWith("G");
    for (String st : names) {
      // call the test method
      if (p.test(st)) {
        System.out.println(st);
      }
    }
  }

  @Test
  public void long_int() {
    long a = 1;
    int b = 1;
    Assert.assertEquals(a, b);
  }

  @Test
  public void long_integer() {
    Long a = 1L;
    Integer b = 1;
    Assert.assertNotEquals(a, b);
  }

  @Test
  public void listFindLong() {
    List<Long> arr = new ArrayList<>();
    arr.add(2L);

    List<Long> from = new ArrayList<>();
    from.add(2L);
    from.add(3L);
    System.out.println(
        arr.stream().filter(o -> from.contains(o)).collect(Collectors.toList()));
  }

  @FunctionalInterface
  public interface Do {
    void print();

    default void description() {
      System.out.println("FunctionalInterface");
    }
  }
}
