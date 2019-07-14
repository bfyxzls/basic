package com.lind.basic.util;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class RandomTest {
  @Test
  public void intListRandomTest() {
    for (int i = 1; i < 5; i++) {
      List<Integer> a1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
      int val1 = RandomUtils.getRandomElement(a1);
      List<Integer> a2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
      int val2 = RandomUtils.getRandomElement(a2);
      Assert.assertNotEquals(val1, 12);
    }
  }
}
