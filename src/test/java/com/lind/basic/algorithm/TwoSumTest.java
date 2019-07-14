package com.lind.basic.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class TwoSumTest {
  @Test
  public void testAddTwoNumbers() {
    ListNode l1 = new ListNode(1);
    l1.next = new ListNode(2);
    ListNode l2 = new ListNode(9);
    l2.next = new ListNode(1);
    ListNode sum = addTwoNumbers(l1, l2);
    while (sum != null) {
      System.out.println("one add one:" + sum.val);
      sum = sum.next;
    }
  }

  @Test
  public void testTwoSum() {
    int[] result = twoSum(new int[]{1, 2, 5, 7}, 9);
    System.out.println("tow sum:" + Arrays.toString(result));
  }

  @Test
  public void testReverse() {
    System.out.println("number 156 reverse:" + reverse(156));
  }

  /**
   * 数字反转.
   *
   * @param x .
   * @return
   */
  public int reverse(int x) {
    int rev = 0;
    while (x != 0) {
      //反转逄法
      int pop = x % 10;
      x /= 10;
      //溢出检查
      if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) {
        return 0;
      }
      if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) {
        return 0;
      }
      rev = rev * 10 + pop;
    }
    return rev;
  }

  /**
   * 两数之和.
   *
   * @param nums   .
   * @param target .
   * @return
   */
  public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      map.put(nums[i], i);
    }
    for (int i = 0; i < nums.length; i++) {
      int complement = target - nums[i];
      if (map.containsKey(complement) && map.get(complement) != i) {
        return new int[]{i, map.get(complement)};
      }
    }
    throw new IllegalArgumentException("No two sum solution");
  }

  /**
   * 两个数相加.
   *
   * @param l1 .
   * @param l2 .
   * @return
   */
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummyHead = new ListNode(0);
    ListNode p = l1;
    ListNode q = l2;
    ListNode curr = dummyHead;
    int carry = 0;
    while (p != null || q != null) {
      int x = (p != null) ? p.val : 0;
      int y = (q != null) ? q.val : 0;
      int sum = carry + x + y;
      carry = sum / 10;
      curr.next = new ListNode(sum % 10);
      curr = curr.next;
      if (p != null) {
        p = p.next;
      }
      if (q != null) {
        q = q.next;
      }
    }
    if (carry > 0) {
      curr.next = new ListNode(carry);
    }
    return dummyHead.next;
  }

  static class ListNode {
    int val;
    ListNode next;

    public ListNode(int x) {
      val = x;
    }

  }
}
