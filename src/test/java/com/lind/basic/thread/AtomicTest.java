package com.lind.basic.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

/**
 * 多线程变量共享方式
 */
public class AtomicTest {
  private static final int THREADS_COUNT = 2;

  public static int count = 0;
  public static volatile int countVolatile = 0;
  public static AtomicInteger atomicInteger = new AtomicInteger(0);
  public static CountDownLatch countDownLatch = new CountDownLatch(2);

  public static void increase() {
    count++;
    countVolatile++;
    atomicInteger.incrementAndGet();
  }

  @Test
  public void increaseDemo() throws InterruptedException {
    Thread[] threads = new Thread[THREADS_COUNT];
    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(() -> {
        for (int i1 = 0; i1 < 1000; i1++) {
          increase();
        }
        countDownLatch.countDown();
      });
      threads[i].start();
    }

    countDownLatch.await();

    System.out.println(count);
    System.out.println(countVolatile);
    System.out.println(atomicInteger.get());
  }
}
