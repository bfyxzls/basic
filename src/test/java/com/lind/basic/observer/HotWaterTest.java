package com.lind.basic.observer;

import org.junit.Test;

/**
 * 观察者模式使用.
 */
public class HotWaterTest {

  @Test
  public void notifyToObservers() {
    Publish publish = new Publish();
    Subscribe1 subscribe1 = new Subscribe1(publish);
    Subscribe2 subscribe2 = new Subscribe2(publish);

    publish.setData("水开了。。。");
  }
}
