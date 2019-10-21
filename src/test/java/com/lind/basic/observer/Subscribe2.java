package com.lind.basic.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 实现java.util.Observer接口的观察者.
 */
public class Subscribe2 implements Observer {
  public Subscribe2(Observable o) {
    o.addObserver(this);        //将该观察者放入待通知观察者里
  }

  @Override
  public void update(Observable o, Object arg) {
    System.out.println("收到通知2:" + ((Publish) o).getData());

  }
}
