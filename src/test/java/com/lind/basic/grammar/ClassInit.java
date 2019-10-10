package com.lind.basic.grammar;

import org.junit.Test;

/**
 * 类初始化的过程.
 */
public class ClassInit {
  @Test
  public void staticFieldInitSelfClass() {
    System.out.println(SubClass.value);
  }

  @Test
  public void arrayDeclareNotInitClass() {
    SuperClass[] superClasses = new SuperClass[10];
  }

  @Test
  public void finalNotInitClass() {
    System.out.println(ConstClass.HELLO_BINGO);
  }

  @Test
  public void staticFieldSequence() {
    System.out.println(Shunxu.i);

  }

  @Test
  public void staticFieldSequence2() {
    System.out.println(Shunxu2.i);

  }


}

/**
 * 被动引用 Demo1:
 * 通过子类引用父类的静态字段，不会导致子类初始化。
 *
 * @author ylb
 */
class SuperClass {
  public static int value = 123;

  static {
    System.out.println("SuperClass init!");
  }
}

class SubClass extends SuperClass {
  static {
    System.out.println("SubClass init!");
  }
}

/**
 * 被动引用 Demo3:
 * 常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化。
 *
 * @author ylb
 */
class ConstClass {
  public static final String HELLO_BINGO = "Hello Bingo";

  static {
    System.out.println("ConstClass init!");
  }

}

/**
 * 静态变量在静态构造方法的前后有影响.
 */
class Shunxu {
  static int i = 1;

  static {
    i = 0;  // 给变量赋值可以正常编译通过
  }
}

/**
 * 静态变量在静态构造方法的前后有影响.
 */
class Shunxu2 {

  static {
    i = 0;  // 给变量赋值可以正常编译通过
  }
  static int i = 1;
}
