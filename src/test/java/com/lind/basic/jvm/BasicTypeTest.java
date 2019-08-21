package com.lind.basic.jvm;

import org.junit.Test;

/**
 * java封装的基本类型可以存储在方法区，也有可能存储在堆上.
 * 五种基本类型实现了常量池：Byte,Short,Integer,Long,Character,Boolean;Float,Double没有实现.
 */
public class BasicTypeTest {

  /**
   * 被封装的基本类型的存储.
   */
  @Test
  public void integer_negative128_positive127Test() {
    Integer a1 = 1; // 常量池
    Integer a2 = new Integer(1);  // 堆
    Integer a3 = Integer.valueOf(1); // 常量池

    System.out.println("a1==a2:" + (a1 == a2));// false
    System.out.println("a1==a3:" + (a1 == a3));// true
    System.out.println("a2==a3:" + (a2 == a3));// false

  }

  /**
   * valueOf将有缓存特点，在-128到127即占用1字节空间时，将存储到常量池，而超过范围时存储在堆上.
   */
  @Test
  public void integer_greater_than_127() {
    Integer a1 = 129;// 堆
    Integer a2 = 129;// 堆
    Integer a3 = Integer.valueOf(129);// 堆
    Integer a4 = Integer.valueOf(129);// 堆
    Integer a5 = 129 * 2;

    System.out.println("a1==a2:" + (a1 == a2));// false
    System.out.println("a1==a3:" + (a1 == a3));// false
    System.out.println("a2==a3:" + (a2 == a3));// false
    System.out.println("a3rue==a4:" + (a3 == a4));// false
    System.out.println("a1+a2==a5:" + (a1 + a2 == a5));// true 它进行了拆箱操作，a1和a2的结果变成了int类型，所以在常量池存储.

  }

  /**
   * 字符串的存储和拼接后存储.
   */
  @Test
  public void string_combine() {
    String hello = "hello world";
    String a1 = "hello"; // 常量池
    String a2 = new String("hello"); // 先在常量池中创建，再在堆中创建
    String a3 = a2.intern();// 常量池
    String a4 = "hello" + " world";// 常量池
    String a5 = a1 + " world";// 堆


    System.out.println("a1==a2:" + (a1 == a2));// false
    System.out.println("a1==a3:" + (a1 == a3));// true
    System.out.println("a2==a3:" + (a2 == a3));// false
    System.out.println("hello==a4:" + (hello == a4));// true
    System.out.println("hello==a5:" + (hello == a5));// false

  }
}
