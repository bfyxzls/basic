package com.lind.basic.thread;

import org.junit.Test;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

public class SimpleAsyncTaskExecutorTest {

  @Test
  public void testAsyncTask() {
    //在循环外面定义它，这样setConcurrencyLimit才有意义
    SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
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
