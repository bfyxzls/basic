package com.lind.basic.asserts;

import java.util.Arrays;
import org.junit.Test;
import org.springframework.util.Assert;

public class NullTest {
  void noNullWarn(String msg) {
    Assert.notNull(msg, "msg不能是null");
    System.out.println("msg=" + msg);
  }

  void noElements(String[] arr) {
    Assert.notNull(arr, "arr列表不能为null");
    Assert.notEmpty(arr, "arr数组没有任何元素");
    Assert.noNullElements(arr, "arr列表里不能有空元素");
    Arrays.stream(arr).forEach(System.out::println);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStrFail() {
    noNullWarn(null);
  }

  @Test
  public void testStrSuccess() {
    noNullWarn("message");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testListFail() {
    noElements(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testListFailForNoElements() {
    noElements(new String[] {});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testListFailForNullElements() {
    noElements(new String[] {"test", null});
  }

  @Test
  public void testListSuccess() {
    noElements(new String[] {"a", "b"});
  }
}
