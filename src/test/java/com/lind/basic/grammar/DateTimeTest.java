package com.lind.basic.grammar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Test;

public class DateTimeTest {
  @Test
  public void localDateString() {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime time = LocalDateTime.now();
    String localTime = df.format(time);
    System.out.println(localTime);
  }
}
