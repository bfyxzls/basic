package com.lind.basic.grammar;

import com.lind.basic.BaseTest;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;

public class CompareTest extends BaseTest {
  @Test
  public void latestMonthYearTest() {
    List<YearMonth> list = Arrays.asList(
        YearMonth.of(2018, 1),
        YearMonth.of(2018, 3),
        YearMonth.of(2018, 2));
    list.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
    YearMonth latest = list.stream().sorted(Comparator.reverseOrder()).findFirst().orElse(null);
    Assert.assertEquals(YearMonth.of(2018, 3), latest);
  }

  @Test
  public void compareObject() {
    Long a = Long.valueOf(1);
    Long b = Long.valueOf(1);
    Assert.assertTrue(Objects.equals(a, b));
  }

  @Test
  public void compareObjectClass() {
    Long a = Long.valueOf(1);
    TestClass testClass = new TestClass();
    Assert.assertFalse(Objects.equals(a, testClass.getAge()));
  }

  class TestClass {
    private Long age;

    public Long getAge() {
      return age;
    }

    public void setAge(Long age) {
      this.age = age;
    }
  }
}
