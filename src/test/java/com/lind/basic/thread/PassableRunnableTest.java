package com.lind.basic.thread;

import com.lind.basic.BaseTest;
import org.junit.Test;

/**
 * 函数式接口，线程阻塞测试.
 */
public class PassableRunnableTest extends BaseTest {
  @Test
  public void test1() throws Exception {
    super.periodicCheck(new PassableRunnable() {
      private boolean passed = false;

      @Override
      public boolean isPassed() {
        return passed;
      }

      @Override
      public void run() {
        System.out.println("test async task");
        passed = true;

      }
    });

  }
}
