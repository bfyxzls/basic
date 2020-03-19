package com.lind.basic.thread;

import org.junit.Test;

public class ThreadLocalTest {
  @Test
  public void threadShare() {
    ThreadLocal<String> info = new ThreadLocal<>();//当前线程有效
    info.set("hello");
    String infoGlobal = "good";
    System.out.println("out:" + Thread.currentThread().getId() + info.get() + infoGlobal);
    new Thread(() -> {
      System.out.println("in:" + Thread.currentThread().getId() + info.get() + infoGlobal);
    }).start();

  }
}
