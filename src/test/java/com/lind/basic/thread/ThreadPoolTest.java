package com.lind.basic.thread;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 关于线程池的测试.
 */
@Slf4j
public class ThreadPoolTest {

  /**
   * 阿里推荐
   */
  @Test
  public void alibaba() throws IOException {
    int corePoolSize = 2;
    int maximumPoolSize = 4;
    long keepAliveTime = 10;
    TimeUnit unit = TimeUnit.SECONDS;
    BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
    ThreadFactory threadFactory = new NameTreadFactory();
    RejectedExecutionHandler handler = new MyIgnorePolicy();
    ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
        workQueue, threadFactory, handler);
    executor.prestartAllCoreThreads(); // 预启动所有核心线程

    for (int i = 1; i <= 10; i++) {
      MyTask task = new MyTask(String.valueOf(i));
      executor.execute(task);
    }

    System.in.read(); //阻塞主线程
  }

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
    Thread.sleep(5000);//阻塞主线程

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
   * 有可能出现线程堆积问题
   */
  @Test
  public void newFixedThreadPool() throws InterruptedException {
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 2000; i++) {
      fixedThreadPool.execute(() ->
          logger.info("fixedThreadPool测试代码:{},time:{}", Thread.currentThread().getName(), LocalDateTime.now()));
    }
    Thread.sleep(5000);
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
   * newScheduledThreadPool按着时间间隔进行周期性任务
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
   * 不建议直接使用它.
   */
  @Test
  public void threadPoolExecutor() {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
        1, //corePoolSize
        100, //maximumPoolSize
        100, //keepAliveTime
        TimeUnit.SECONDS, //unit
        new LinkedBlockingDeque<>(100));//workQueue

    for (int i = 0; i < 5; i++) {
      final int taskIndex = i;
      executor.execute(() -> {
        System.out.println(taskIndex);
        try {
          Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }
  }

  /**
   * 分而置之.
   */
  @Test
  public void forkJoin() {
    SumTask sumTask = new SumTask(1, 999999);
    sumTask.fork();
    System.out.print("result:" + sumTask.join());
  }

  /**
   * 自定义线程工厂
   */
  static class NameTreadFactory implements ThreadFactory {

    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
      Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
      System.out.println(t.getName() + " has been created");
      return t;
    }
  }

  /**
   * 自定义绝决策略
   */
  public static class MyIgnorePolicy implements RejectedExecutionHandler {

    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
      doLog(r, e);
    }

    private void doLog(Runnable r, ThreadPoolExecutor e) {
      // 可做日志记录等
      System.err.println(r.toString() + " rejected");
    }
  }

  /**
   * 自定义线程任务.
   */
  static class MyTask implements Runnable {
    private String name;

    public MyTask(String name) {
      this.name = name;
    }

    @Override
    public void run() {
      try {
        System.out.println(this.toString() + " is running!");
        Thread.sleep(3000); //让任务执行慢点
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return "MyTask [name=" + name + "]";
    }
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

  /**
   * 使用RecursiveTask实现一个累加的功能，使用分而治之的思想，实现分段求和后汇总.
   */
  public class SumTask extends RecursiveTask<Integer> {

    private Integer start = 0;
    private Integer end = 0;

    public SumTask(int start, int end) {
      this.start = start;
      this.end = end;
    }

    @Override
    protected Integer compute() {

      if (end - start < 100) {
        //小于100时直接返回结果
        int sumResult = 0;
        for (int i = start; i <= end; i++) {
          sumResult += i;
        }
        return sumResult;
      } else {
        //大于一百时进行分割
        int middle = (end + start) / 2;
        SumTask leftSum = new SumTask(this.start, middle);
        SumTask rightSum = new SumTask(middle, this.end);
        leftSum.fork();
        rightSum.fork();
        return leftSum.join() + rightSum.join();
      }
    }

  }
}


