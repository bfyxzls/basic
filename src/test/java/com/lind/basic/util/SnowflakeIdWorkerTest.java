package com.lind.basic.util;

import org.junit.Test;

public class SnowflakeIdWorkerTest {

  SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);

  @Test
  public void bulkId() {
    for (int i = 0; i < 10; i++) {
      System.out.println(snowflakeIdWorker.nextId());
    }
  }
}
