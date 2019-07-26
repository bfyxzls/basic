package com.lind.basic.algorithm;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.junit.Test;

/**
 * 单例模式测试.
 */
public class SingletonTest {

  @Test
  public void singleTest() {
    for (int i = 0; i < 15; i++) {
      new Thread(() -> {
        SingletonObj.getInstance();
      }).start();
    }
  }

  @Test
  public void threadLocalTest() {
    SingletonObj.getInstance().setContext(
        ImmutableMap.of("name", "lind", "role", "admin"));
    System.out.println(SingletonObj.getInstance().getContext());
  }

}

/**
 * 单例模式测试.
 */
class SingletonObj {
  private ThreadLocal<Map<String, String>> threadLocal;

  private SingletonObj() {
    System.out.println("SingletonObj init.");
    threadLocal = new ThreadLocal<>();
  }

  /**
   * 获取单例对象.
   *
   * @return
   */
  public static SingletonObj getInstance() {
    return SingletonObj.instance.context;
  }

  /**
   * 获取上下文中的信息
   *
   * @return
   */
  public Map<String, String> getContext() {
    return threadLocal.get();
  }

  /**
   * 用户上下文中放入信息
   *
   * @param map
   */
  public void setContext(Map<String, String> map) {
    threadLocal.set(map);
  }

  /**
   * 静态内部类-实现单例.
   * 使用内部类的好处是，静态内部类不会在单例加载时就加载，而是在调用getInstance()方法时才进行加载，达到了类似懒汉模式的效果，而这种方法又是线程安全的.
   */
  private static class instance {
    private static final SingletonObj context = new SingletonObj();
  }
}