package com.lind.basic.util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import org.junit.Assert;
import org.junit.Test;

public class LinkedListQueueStack {
  @Test
  public void linkList() {
    LinkedList<Integer> idList = new LinkedList<>();
    // 堆栈LIFO
    idList.push(1);
    idList.push(2);
    idList.push(3);
    Assert.assertEquals(Integer.valueOf(3), idList.pop());
    Assert.assertEquals(Integer.valueOf(2), idList.pop());
    Assert.assertEquals(Integer.valueOf(1), idList.pop());

    // 队列FIFO
    idList.add(1);
    idList.add(2);
    idList.add(3);
    Assert.assertEquals(Integer.valueOf(1), idList.pop());
    Assert.assertEquals(Integer.valueOf(2), idList.pop());
    Assert.assertEquals(Integer.valueOf(3), idList.pop());

  }

  /**
   * FIFO.
   */
  @Test
  public void queue() {
    Queue<Integer> queue = new LinkedList<>();
    queue.add(1);
    queue.add(2);
    queue.add(3);
    Assert.assertEquals(Integer.valueOf(1), queue.poll());
    Assert.assertEquals(Integer.valueOf(2), queue.poll());
    Assert.assertEquals(Integer.valueOf(3), queue.poll());
  }

  /**
   * LIFO.
   */
  @Test
  public void stack() {
    Stack<Integer> stack = new Stack<>();
    stack.push(1);
    stack.push(2);
    stack.push(3);
    Assert.assertEquals(Integer.valueOf(3), stack.pop());
    Assert.assertEquals(Integer.valueOf(2), stack.pop());
    Assert.assertEquals(Integer.valueOf(1), stack.pop());
  }
}
