package com.lind.basic.thread;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 关于线程池的测试.
 */
@Slf4j
public class ThreadPoolTest {

  /**
   * 方法强制为同步方法.
   */
  synchronized void queue() {
    logger.info("printMessage synchronized result:{}，thread:{}", LocalDateTime.now().toString(), Thread.currentThread().getId());
  }

  @Test
  public void synchronizedTest() throws Exception {
    for (int i = 0; i < 5; i++) {
      new Thread(() -> queue()).start();
    }
    TimeUnit.MILLISECONDS.sleep(5000);

  }

  /**
   * newCachedThreadPool线程自动分配，不能控制上限.
   */
  @Test
  public void newCachedThreadPool() {
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    for (int i = 0; i < 20; i++) {
      cachedThreadPool.execute(new Runnable() {
        @Override
        public void run() {
          logger.info("cachedThreadPool测试代码:{}", Thread.currentThread().getName());
        }
      });
    }
  }

  /**
   * newFixedThreadPool控制最大的线程数，可以控制上限.
   */
  @Test
  public void newFixedThreadPool() {
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 20; i++) {
      fixedThreadPool.execute(new Runnable() {
        @Override
        public void run() {
          logger.info("fixedThreadPool测试代码:{}", Thread.currentThread().getName());
        }
      });
    }
  }

  /**
   * newSingleThreadExecutor只有一个线程，当这个线程因为异常结束，会有一个新的线程来替代它.
   */
  @Test
  public void newSingleThreadExecutor() {
    ExecutorService newSingleThread = Executors.newSingleThreadExecutor();
    for (int i = 0; i < 20; i++) {
      newSingleThread.execute(new Runnable() {
        @Override
        public void run() {
          logger.info("newSingleThread测试代码:{}", Thread.currentThread().getName());
        }
      });
    }
  }

  /**
   * newScheduledThreadPool两种方式不同的地方是任务的执行时间，如果间隔时间大于任务的执行时间，任务不受执行时间的影响。如果间隔时间小于任务的执行时间，那么任务执行结束之后，会立马执行，至此间隔时间就会被打乱
   */
  @Test
  public void newScheduledThreadPool() throws InterruptedException {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
    executor.scheduleAtFixedRate(() -> {
      long start = new Date().getTime();
      System.out.println("scheduleAtFixedRate 开始执行时间:" +
          DateFormat.getTimeInstance().format(new Date()));
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      long end = new Date().getTime();
      System.out.println("scheduleAtFixedRate 执行花费时间=" + (end - start) / 1000 + "m");
      System.out.println("scheduleAtFixedRate 执行完成时间：" + DateFormat.getTimeInstance().format(new Date()));
      System.out.println("======================================");
    }, 0, 1, TimeUnit.SECONDS);//initialDelay初始化延时,period:两次执行最小间隔时间

    Thread.sleep(1000 * 10);
  }

  @Test
  public void runnableTest() {
    MyThread myThread = new MyThread();
    myThread.run();
  }

  /**
   * 不建议直接使用线程，应该使用线程池.
   */
  class MyThread implements Runnable {

    public MyThread() {
    }

    public void run() {
      System.out.println("MyThread类的run()方法在运行");
    }
  }


}


