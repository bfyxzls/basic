package com.lind.basic.thread;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.rules.Stopwatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 多线程并发修改数据.
 */
public class VolatileTest {
    private static final int THREADS_COUNT = 20;
    public static volatile int race = 0;
    // 线程等待
    private static CountDownLatch countDownLatch = new CountDownLatch(THREADS_COUNT);

    /**
     * 不加锁
     */
    public static void increase() {
        race++;
    }

    /**
     * 同步锁
     */
    public static synchronized void syncIncrease() {
        race++;
    }

    /**
     * volatile线程不安全
     *
     * @throws InterruptedException
     */
    @Test
    public void countIncrease() throws InterruptedException {
        StopWatch stopwatch = StopWatch.createStarted();
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int i1 = 0; i1 < 10000; i1++) {
                    increase();
                }
                //将count值减1
                countDownLatch.countDown();
            });
            threads[i].start();
        }
        //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        countDownLatch.await();
        System.out.println(race);
        stopwatch.stop();
        System.out.println(stopwatch.getTime(TimeUnit.MILLISECONDS));
    }

    @Test
    public void countSyncIncrease() throws InterruptedException {
        Thread[] threads = new Thread[THREADS_COUNT];
        StopWatch stopwatch = StopWatch.createStarted();
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int i1 = 0; i1 < 10000; i1++) {
                    syncIncrease();
                }
                //将count值减1
                countDownLatch.countDown();
            });
            threads[i].start();
        }
        //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        countDownLatch.await();
        System.out.println(race);
        stopwatch.stop();
        System.out.println(stopwatch.getTime(TimeUnit.MILLISECONDS));
    }
}
