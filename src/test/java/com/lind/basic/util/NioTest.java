package com.lind.basic.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

/**
 * 非阻塞同步IO测试.
 */
public class NioTest {
  private static List<String> list = new ArrayList<String>();

  public static void add() {
    list.add("anyString");
  }

  public static int size() {
    return list.size();
  }

  @Test
  public void nioReadFile() {
    try {
      RandomAccessFile rdf = new RandomAccessFile(ResourceUtils.getFile("classpath:niotest.txt"), "rw");
      //利用channel中的FileChannel来实现文件的读取
      FileChannel inChannel = rdf.getChannel();
      //设置缓冲区容量为10
      ByteBuffer buf = ByteBuffer.allocate(10);
      //从通道中读取数据到缓冲区，返回读取的字节数量
      int byteRead = inChannel.read(buf);
      //数量为-1表示读取完毕。
      while (byteRead != -1) {
        //切换模式为读模式，其实就是把postion位置设置为0，可以从0开始读取
        buf.flip();
        //如果缓冲区还有数据
        while (buf.hasRemaining()) {
          //输出一个字符
          System.out.print((char) buf.get());
        }
        //数据读完后清空缓冲区
        buf.clear();
        //继续把通道内剩余数据写入缓冲区
        byteRead = inChannel.read(buf);
      }
      //关闭通道
      rdf.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  /**
   * synchronized关键字可以将任何一个Object对象作为同步对象来看待.
   * 而Java为每个Object都实现了等待/通知（wait/notify）机制的相关方法，
   * 它们必须用在synchronized关键字同步的Object的临界区内。通过调用wait()方法可以使处于临界区内的线程进入等待状态，
   * 同时释放被同步对象的锁。而notify()方法可以唤醒一个因调用wait操作而处于阻塞状态中的线程，使其进入就绪状态。
   * 被重新唤醒的线程会视图重新获得临界区的控制权也就是锁，并继续执行wait方法之后的代码。如果发出notify操作时没有处于
   * 阻塞状态中的线程，那么该命令会被忽略。
   */
  @Test
  public void notifyThread() throws Exception {
    /*
        等待/通知机制在我们生活中比比皆是，一个形象的例子就是厨师和服务员之间就存在等待/通知机制。
    1. 厨师做完一道菜的时间是不确定的，所以菜到服务员手中的时间是不确定的；
    2. 服务员就需要去“等待（wait）”；
    3. 厨师把菜做完之后，按一下铃，这里的按铃就是“通知（nofity）”；
    4. 服务员听到铃声之后就知道菜做好了，他可以去端菜了。

    用专业术语讲：

    等待/通知机制，是指一个线程A调用了对象O的wait()方法进入等待状态，而另一个线程B调用了对象O的notify()/notifyAll()方法，
    线程A收到通知后退出等待队列，进入可运行状态，进而执行后续操作。上诉两个线程通过对象O来完成交互，而对象上的wait()方法
    和notify()/notifyAll()方法的关系就如同开关信号一样，用来完成等待方和通知方之间的交互工作。
     */
    Object lock = new Object();
    ThreadA a = new ThreadA(lock);
    a.start();

    Thread.sleep(50);

    ThreadB b = new ThreadB(lock);
    b.start();

    Thread.sleep(20000);
  }


  class ThreadB extends Thread {
    private Object lock;

    public ThreadB(Object lock) {
      super();
      this.lock = lock;
    }

    @Override
    public void run() {
      try {
        synchronized (lock) {
          for (int i = 0; i < 10; i++) {
            add();
            if (size() == 5) {
              lock.notify();
              System.out.println("已发出通知！");
            }
            System.out.println("添加了" + (i + 1) + "个元素!");
            Thread.sleep(1000);
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

  class ThreadA extends Thread {

    private Object lock;

    public ThreadA(Object lock) {
      super();
      this.lock = lock;
    }

    @Override
    public void run() {
      try {
        synchronized (lock) {
          if (size() != 5) {
            System.out.println("wait begin a"
                + System.currentTimeMillis());
            lock.wait();
            System.out.println("wait end  a"
                + System.currentTimeMillis());
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }
}
