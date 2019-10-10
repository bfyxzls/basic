package com.lind.basic.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Test;

public class FutureTaskTest {

  /**
   * 异步返回结果.
   */
  @Test
  public void test() throws Exception {
    long startTime = System.currentTimeMillis();
    Callable<Integer> calculateCallable = () -> {
      Thread.sleep(2000);//模拟耗时时间
      int result = 1 + 2;
      return result;
    };
    FutureTask<Integer> calculateFutureTask = new FutureTask<>(calculateCallable);
    Thread t1 = new Thread(calculateFutureTask);
    t1.start();

    //现在加入Thread运行的是一个模拟远程调用耗时的服务，并且依赖他的计算结果（比如网络计算器）
    try {
      //模拟耗时任务，主线程做自己的事情，体现多线程的优势
      Thread.sleep(3000);
      int a = 3 + 5;
      Integer result = calculateFutureTask.get();
      System.out.println("result = " + (a + result));//模拟主线程依赖子线程的运行结果
      long endTime = System.currentTimeMillis();
      System.out.println("time = " + (endTime - startTime) + "ms");
    } catch (InterruptedException | ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Thread.sleep(60000);
  }

  @Test
  public void waitFuture() throws Exception {
    Future<Integer> future = new SquareCalculator().calculate(10);

    while (!future.isDone()) {
      System.out.println("Calculating...");
      Thread.sleep(300);
    }

    Integer result = future.get();
    System.out.println("result=" + result);
  }

  @Test(expected = TimeoutException.class)
  public void waitFutureWarn() throws Exception {
    Future<Integer> future = new SquareCalculator().calculate(10);
    Integer result = future.get(500, TimeUnit.MILLISECONDS);

  }

  @Test
  public void dealWait() throws Exception {
    Future<Integer> future = new SquareCalculator().calculate(10);
    System.out.println("result=" + future.get());
  }
}

class SquareCalculator {

  private ExecutorService executor
      = Executors.newSingleThreadExecutor();

  public Future<Integer> calculate(Integer input) {
    return executor.submit(() -> {
      Thread.sleep(3000);
      return input * input;
    });
  }
}