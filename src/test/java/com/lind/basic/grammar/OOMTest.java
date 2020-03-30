package com.lind.basic.grammar;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OOMTest {

    public static void hi() {
        hi();
    }

    @Test
    public void outOfMemoryError() {
        try {
            List<Object> list = new ArrayList<Object>();
            for (; ; ) {
                list.add(new Object()); // 创建对象速度可能高于jvm回收速度
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stackOverFlowError() {
        try {
            hi(); // 递归造成StackOverflowError 这边因为每运行一个方法将创建一个栈帧，栈帧创建太多无法继续申请到内存扩展
        } catch (StackOverflowError e) {
            e.printStackTrace();
        }
    }
}
