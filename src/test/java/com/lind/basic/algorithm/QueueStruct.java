package com.lind.basic.algorithm;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class QueueStruct {
  //元素集合
  private LinkedList<Object> list=new LinkedList<Object>();
  //计数器(同步),判断集合元素数量
  private AtomicInteger count=new AtomicInteger();
  //集合上限与下限,final必须指定初值
  private final int minSize=0;
  private final int maxSize;
  //构造器指定最大值
  public QueueStruct(int maxSize) {
    this.maxSize = maxSize;
  }

  //初始化对象,用于加锁,也可直接用this
  private Object lock=new Object();
  //put方法:往集合中添加元素,如果集合元素已满,则此线程阻塞,直到有空间再继续
  public void put(Object obj){
    synchronized (lock) {
      while(count.get()==this.maxSize){
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();}
      }
      list.add(obj);
      //计数器加一
      count.incrementAndGet();
      System.out.println("放入元素:"+obj);
      //唤醒另一个线程,(处理极端情况:集合一开始就是空,此时take线程会一直等待)
      lock.notify();
    }
  }
  //take方法:从元素中取数据,如果集合为空,则线程阻塞,直到集合不为空再继续
  public Object pop(){
    Object result=null;
    synchronized(lock){
      while(count.get()==this.minSize){
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();}
      }
      //移除第一个
      result=list.removeFirst();
      //计数器减一
      count.decrementAndGet();
      System.out.println("拿走元素:"+result);
      //唤醒另一个线程,(处理极端情况:集合一开始就是满的,此时put线程会一直等待)
      lock.notify();
    }
    return result;
  }
  public int getSize(){
    return this.count.get();
  }

  @Test
  public static void test() throws Exception {
    //创建集合容器
    QueueStruct queue = new QueueStruct(5);
    queue.put("1");
    queue.put("2");
    queue.put("3");
    queue.put("4");
    queue.put("5");
    System.out.println("当前容器长度为:" + queue.getSize());
    Thread t1 = new Thread(() -> {
      queue.put("6");
      queue.put("7");
    }, "t1");
    Thread t2 = new Thread(() -> {
      Object take1 = queue.pop();
      Object take2 = queue.pop();
    }, "t2");
    //测试极端情况,两秒钟后再执行另一个线程
    t1.start();
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    t2.start();

    TimeUnit.SECONDS.sleep(2000);

  }

}
