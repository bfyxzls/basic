package com.lind.basic.thread;

import org.junit.Test;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

public class SimpleAsyncTaskExecutorTest {
  SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();

  @Test
  public void testAsyncTask() {
    simpleAsyncTaskExecutor.setConcurrencyLimit(10);
    for (int i = 0; i < 100; i++) {
      simpleAsyncTaskExecutor.execute(() -> {
        System.out.println("测试代码");
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }
  }
}
