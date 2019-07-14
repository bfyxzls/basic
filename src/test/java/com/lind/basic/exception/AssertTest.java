package com.lind.basic.exception;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

public class AssertTest {
  @Test(expected = IllegalArgumentException.class)
  public void assertIsNull() {
    String a = null;
    org.springframework.util.Assert.notNull(a, "a is not null!");
  }

  @Test(expected = IllegalArgumentException.class)
  public void assertIsEmpty() {
    org.springframework.util.Assert.notEmpty(ImmutableMap.of(), "not empty map");
  }
}
