package com.lind.basic.grammar;

import org.junit.Test;

/**
 * @author zhangzhanling
 * @description 基本数据类型的比较.
 * @date 2020/3/27 9:03
 */
public class IntegerTest {

    /**
     * java 保存有8中基本类型的“池” 整型是范围是-128~127
     * 所以如果整数不超过127则引用指向同一“池”
     * 所以他们指向的是同一对象
     * 超过127 就不是同一对象
     * 原因在于，在进行自动拆装箱时，编译器会使用Integer.valueof()来创建Integer实例。
     * 也就是说在-128到127之间的数字是同一引用地址（对于-128~127之间的数，Integer不将其分配在堆区），而其它范围就是不同的引用地址。
     */
    @Test
    public void Ins1() {
        Integer m = 200;
        Integer n = 200;
        System.out.println(m == n); // false
        Integer x = 6;
        Integer y = 6;
        System.out.println(x == y); // true
        Integer a = 128;
        Integer b = Integer.valueOf(128);
        System.out.println(a == b); // false
    }

    /**
     * Integer在-128~127之间时，它被存储到“常量池”中，其它在分配在堆内存中
     */
    @Test
    public void equalAndSymbol() {
        Integer a = new Integer(1);
        Integer b = 1;
        System.out.println(a == b);
        System.out.println(a.equals(b));
    }

    /**
     * Integer是一个被封装的类型，可以为null，当它的值是-128~127之间时，使用Integer a=1或者Integer.valueof(1)赋值时，它指向的是同一个地址
     */
    @Test
    public void EqualInteger() {
        System.out.println(Integer.valueOf("127") == Integer.valueOf("127"));
        System.out.println(Integer.valueOf("128") == Integer.valueOf("128"));
        System.out.println(Integer.valueOf("128").equals(Integer.valueOf("128")));
        System.out.println(Integer.parseInt("128") == Integer.valueOf("128"));
    }
}
