package com.lind.basic.thread;

import com.lind.basic.BaseTest;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.junit.Test;

public class NioTest {

  public static void method1() {
    RandomAccessFile aFile = null;
    try {
      ClassLoader classLoader = BaseTest.class.getClassLoader();
      File file = new File(classLoader.getResource("niotest.txt").getFile());
      aFile = new RandomAccessFile(file, "rw");
      FileChannel fileChannel = aFile.getChannel();
      ByteBuffer buf = ByteBuffer.allocate(5);//缓冲区大小
      int bytesRead = fileChannel.read(buf);
      System.out.println(bytesRead);
      while (bytesRead != -1) {
        System.out.println("begin:" + bytesRead);
        buf.flip();//向下便宜一个缓冲区的大小
        while (buf.hasRemaining()) {
          System.out.print((char) buf.get());
        }
        buf.compact();
        bytesRead = fileChannel.read(buf);
        System.out.println("\nend:" + bytesRead);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (aFile != null) {
          aFile.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * nio服务端.
   *
   * @throws Exception .
   */
  @Test
  public void nioServer() throws Exception {
    new NIOServerDemo().go();
  }

  /**
   * nio客户端.
   *
   * @throws Exception .
   */
  @Test
  public void nioClient() throws Exception {
    final NIOClientDemo client = new NIOClientDemo();
    Thread receiver = new Thread(client::receiveFromUser);
    receiver.start();
    client.talk();
  }

  @Test
  public void nioReadFile() {
    method1();
  }

  @Test
  public void bufferInfo() {
    ByteBuffer buf = ByteBuffer.allocate(11);
    buf.put((byte) 1);
    buf.put((byte) 2);
    System.out.println("buf=" + buf.position());
    buf.flip();
    System.out.println("buf=" + buf.position());
  }

}
