package com.lind.basic.algorithm;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * 位运算.
 */
public class BitTest {
  @Test
  public void odd() {
    Assert.assertTrue((3 & 1) == 1);
    Assert.assertTrue((7 & 1) == 1);
    Assert.assertTrue((21 & 1) == 1);
  }

  /**
   * add.
   *
   * @param a .
   * @param b .
   * @return
   */
  public int add(int a, int b) {
    int res = a;
    int xor = a ^ b;
    //得到原位和
    int forward = (a & b) << 1;
    //得到进位和
    if (forward != 0) {
      //若进位和不为0，则递归求原位和+进位和
      res = add(xor, forward);
    } else {
      res = xor;
      //若进位和为0，则此时原位和为所求和
    }
    return res;
  }

  @Test
  public void addTest() {
    Assert.assertEquals(21, add(17, 4));
  }

  @Test
  public void binContain() {
    int a = 1 + 2 + 4 + 8 + 32;
    Assert.assertEquals(2, a & 2);
    Assert.assertEquals(4, a & 4);
    Assert.assertEquals(8, a & 8);
    Assert.assertEquals(0, a & 16);
  }

  @Test
  public void intMax() {
    Map<String, Object> arr = new HashMap<>();
    arr.put("id", "1561951092000");
    Long val = MapUtils.getLong(arr, "id") / 1000;
    Integer value = Integer.parseInt(val.toString());
    System.out.println(value);
  }
}
