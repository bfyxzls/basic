package com.lind.basic.event;

/**
 * @author zhangzhanling
 * @description
 * @date 2020/3/20 9:42
 */
public interface ApplicationListener<T> {
  /**
   * 事件处理.
   *
   * @param eventObject
   */
  void handler(T eventObject) throws InterruptedException;
}
