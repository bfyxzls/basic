package com.lind.basic.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.junit.Test;
import org.springframework.util.Assert;

public class MapTest {
  @Test(expected = NullPointerException.class)
  public void addAllEmpty() {
    Map<String, String> user = new HashMap<>();
    user.put("name", "zzl");
    user.putAll(null);
  }

  @Test()
  public void addAllNotEmpty() {
    Map<String, String> user = new HashMap<>();
    user.put("name", "zzl");
    Map<String, String> ext = new HashMap<>();
    if (MapUtils.isNotEmpty(ext)) {
      user.putAll(ext);
    }
  }

  @Test
  public void listIterator() {
    List<Integer> array = Arrays.asList(1, 2, 3, 4, 5);
    List<List<Integer>> ret = CommonUtils.split(array, 2);

    ret.forEach(o -> System.out.println(o));

  }

  @Test
  public void emptyMap() {
    Map<String, Object> test = null;
    Assert.isTrue(MapUtils.isEmpty(test), "ok");
  }

  /**
   * 扰动法+拉链法.
   */
  void moniMap(Map<Integer, Map<String, String>> moni, String val, int length) {
    int index = (length - 1) & val.hashCode();
    if (moni.containsKey(index)) {
      Map<String, String> map = moni.get(index);
      map.put(val, val);
    } else {
      moni.put(index, new HashMap<String, String>() {
        {
          put(val, val);
        }
      });
    }
  }

  /**
   * 原版-扰动法+拉链法.
   *
   * @param list .
   * @param val  .
   */
  void moniLinkList(LinkedList[] list, String val) {
    int length = list.length;
    int index = (length - 1) & val.hashCode();
    LinkedList linkedList = list[index];
    if (linkedList != null) {
      linkedList.add(val);
    } else {
      linkedList = new LinkedList();
      linkedList.add(val);
    }
    list[index] = linkedList;
  }

  @Test
  public void moniLinkListTest() {
    LinkedList[] list = new LinkedList[8];
    moniLinkList(list, "a");
    moniLinkList(list, "b");
    moniLinkList(list, "c");
    moniLinkList(list, "d");
    moniLinkList(list, "e");
    moniLinkList(list, "f");
    moniLinkList(list, "zzl");
    for (int i = 0; i < list.length; i++) {
      System.out.print(list[i] + " ");
    }
  }

  @Test
  public void moniTest() {
    int len = 4;
    Map<Integer, Map<String, String>> moni = new HashMap<>();
    moniMap(moni, "a", len);
    moniMap(moni, "b", len);
    moniMap(moni, "c", len);
    moniMap(moni, "b", len);
    moniMap(moni, "e", len);
    moniMap(moni, "zzl", len);
    moniMap(moni, "zhl", len);
    moniMap(moni, "zhz", len);
    System.out.println(moni);
  }

  /**
   * 扰动函数求位置 (n - 1) & hash 判断当前元素存放的位置,出现冲突就通过拉链法解决冲突.
   * 所谓冲突，就是不同的hashcode，在进行(n-1)&hash时得到了相同的结果。
   * 当 lenth = 2n 时，X % length = X & (length - 1)
   * 也就是说，长度为2的n次幂时，模运算 % 可以变换为按位与 & 运算。
   */
  @Test
  public void perturbation() {
    Map<Integer, Map<String, String>> moni = new HashMap<>();
    String key1 = "zzl";
    String key2 = "lr";
    String key3 = "ss";
    String key4 = "zhz";

    int len = 4;
    int index1 = (len - 1) & key1.hashCode();
    int index2 = (len - 1) & key2.hashCode();
    int index3 = (len - 1) & key3.hashCode();
    int index4 = (len - 1) & key4.hashCode();

    StringBuffer str = new StringBuffer();
    str.append("index1=" + index1);
    str.append("\nindex2=" + index2);
    str.append("\nindex3=" + index3);
    str.append("\nindex4=" + index4);

    System.out.println(str.toString());
  }

  int position(String a, int length) {
    int i = a.hashCode() & (length - 1);
    System.out.println(String.format("a=%s,code=%s,index=%s", a, a.hashCode(), i));
    return i;
  }

  @Test
  public void arrayIndex() {
    int len = 4;
    position("a", len);
    position("b", len);
    position("c", len);
    position("d", len);
    position("e", len);
    position("dfadsfds", len);
    position("ab", len);
    position("cd", len);
    position("adfasdfasd", len);
  }

  /**
   * 分子是2的N次幂时，它与取模结果相同，并且速度比10进制求模要快.
   */
  @Test
  public void model() {
    System.out.println(9 % 4);
    System.out.println((4 - 1) & 9);
    System.out.println("zzl".hashCode() % 8);
    System.out.println("d".hashCode() % 8);

  }
}
