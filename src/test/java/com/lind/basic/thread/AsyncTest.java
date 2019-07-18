package com.lind.basic.thread;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest()
@EnableAsync
public class AsyncTest {

  /**
   * 方法强制为同步方法.
   */
  synchronized void queue() {
    System.out.println("printMessage synchronized result:" + LocalDateTime.now().toString()
        + "，threadid:" + Thread.currentThread().getId());
  }

  @Test
  public void synchronizedTest() throws Exception {
    for (int i = 0; i < 5; i++) {
      new Thread(() -> queue()).start();
    }
    TimeUnit.MILLISECONDS.sleep(5000);

  }

  /**
   * 线程线的测试.
   */
  @Test
  public void threadPoolTest() {
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    cachedThreadPool.execute(new Runnable() {
      @Override
      public void run() {
        System.out.println("测试代码");
      }
    });

  }

  @Test
  public void runable() {
    MyThread myThread = new MyThread();
    myThread.run();
  }

  class MyThread implements Runnable {

    public MyThread() {
    }

    public void run() {
      System.out.println("MyThread类的run()方法在运行");
    }
  }


}


