package com.lind.basic.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Set;
import java.util.TreeMap;
import org.junit.Assert;
import org.junit.Test;

public class CollectionTest {
  @Test
  public void randomAccess() {
    Assert.assertTrue(new ArrayList<Integer>() instanceof RandomAccess);
    Assert.assertFalse(new LinkedList<Integer>() instanceof RandomAccess);
  }

  @Test
  public void ensureCapacity() {
    Integer[] arr0 = {1, 2};
    arr0 = Arrays.copyOf(arr0, 3);
    arr0[2] = 3;
    Assert.assertEquals(3, arr0.length);
  }

  /**
   * 从指定源数组中复制一个数组，复制从指定的位置开始，到目标数组的指定位置结束。
   * 从 src 引用的源数组到 dest 引用的目标数组，数组组件的一个子序列被复制下来。
   * 被复制的组件的编号等于 length 参数。源数组中位置在 srcPos 到 srcPos+length-1
   * 之间的组件被分别复制到目标数组中的 destPos 到 destPos+length-1 位置.
   */
  @Test
  public void copyArray() {
    Integer[] arr0 = {1, 2};
    Integer[] arr1 = new Integer[2];
    //elementData:源数组;index源数组中的起始位置;elementData：目标数组；index + 1：目标数组中的起始位置； size - index：要复制的数组元素的数量；
    System.arraycopy(arr0, 0, arr1, 0, arr0.length);
    System.out.println(arr1);
  }

  @Test
  public void copyArrayList() {
    ArrayList<Integer> arrayList = new ArrayList<>();
    arrayList.add(1);
    arrayList.add(2);
    ArrayList<Integer> des = new ArrayList<>();
    des.addAll(arrayList);
    System.out.println(des);
  }

  @Test
  public void hashcode() {
    Demo demo1 = new Demo();
    demo1.setName("zzl");
    Demo demo2 = new Demo();
    demo2.setName("zzl");
    Assert.assertEquals(demo1.getName().hashCode(), demo2.getName().hashCode());
    Assert.assertEquals(demo1.hashCode(), demo2.hashCode()); // 重写equals和hashcode之后它们才是相待的，类似于字段值的比较了

  }

  @Test
  public void sortable() {
    ArrayList<Integer> arrayList = new ArrayList<Integer>();
    arrayList.add(-1);
    arrayList.add(3);
    arrayList.add(3);
    arrayList.add(-5);
    arrayList.add(7);
    arrayList.add(4);
    arrayList.add(-9);
    arrayList.add(-7);
    System.out.println("原始数组:");
    System.out.println(arrayList);

    // 定制排序的用法
    Collections.sort(arrayList, (o1, o2) -> o2.compareTo(o1));
    System.out.println("定制排序后：");
    System.out.println(arrayList);
  }


  @Test
  public void sortableCompare() {
    ArrayList<Integer> arrayList = new ArrayList<Integer>();
    arrayList.add(-1);
    arrayList.add(3);
    arrayList.add(3);
    arrayList.add(-5);
    arrayList.add(7);
    arrayList.add(4);
    arrayList.add(-9);
    arrayList.add(-7);
    System.out.println("原始数组:");
    System.out.println(arrayList);
    // 定制排序的用法
    Collections.sort(arrayList, new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return o1.compareTo(o2);
      }
    });
    System.out.println("定制排序后：");
    System.out.println(arrayList);
  }

  @Test
  public void objectMapSort() {
    TreeMap<Person, String> pdata = new TreeMap<Person, String>();
    pdata.put(new Person("张三", 30), "zhangsan");
    pdata.put(new Person("李四", 20), "lisi");
    pdata.put(new Person("王五", 10), "wangwu");
    pdata.put(new Person("小红", 5), "xiaohong");
    // 得到key的值的同时得到key所对应的值
    Set<Person> keys = pdata.keySet();
    for (Person key : keys) {
      System.out.println(key.getAge() + "-" + key.getName());
    }
  }

  @Test
  public void objectSetSort() {
    TreeMap<Person, String> pdata = new TreeMap<Person, String>();
    pdata.put(new Person("张三", 30), "zhangsan");
    pdata.put(new Person("李四", 20), "lisi");
    pdata.put(new Person("王五", 10), "wangwu");
    pdata.put(new Person("小红", 5), "xiaohong");
    // 得到key的值的同时得到key所对应的值
    Set<Person> keys = pdata.keySet();
    for (Person key : keys) {
      System.out.println(key.getAge() + "-" + key.getName());
    }
  }

  @Test
  public void arrayListIterator() {
    ArrayList<Integer> arrayList = new ArrayList<>();
    arrayList.add(1);
    arrayList.add(3);
    arrayList.add(5);
    arrayList.add(7);
    arrayList.add(9);
    // 第一种：通过迭代器遍历
    System.out.print("通过迭代器遍历:");
    Iterator<Integer> it = arrayList.iterator();
    while (it.hasNext()) {
      System.out.print(it.next() + " ");
    }
    System.out.println();

    // 第二种：通过索引值遍历
    System.out.print("通过索引值遍历:");
    for (int i = 0; i < arrayList.size(); i++) {
      System.out.print(arrayList.get(i) + " ");
    }
    System.out.println();

    // 第三种：for循环遍历
    System.out.print("for循环遍历:");
    for (Integer number : arrayList) {
      System.out.print(number + " ");
    }
  }

  class Demo {
    private String name;
    private String email;

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Demo demo = (Demo) o;
      return Objects.equals(name, demo.name)
          &&
          Objects.equals(email, demo.email);
    }

    @Override
    public int hashCode() {

      return Objects.hash(name, email);
    }
  }


  /**
   * person对象没有实现Comparable接口，所以必须实现，这样才不会出错，才可以使treemap中的数据按顺序排列.
   * 前面一个例子的String类已经默认实现了Comparable接口，详细可以查看String类的API文档，另外其他
   * 像Integer类等都已经实现了Comparable接口，所以不需要另外实现了
   */
  class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person(String name, int age) {
      super();
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    /**
     * TODO重写compareTo方法实现按年龄来排序.
     */
    @Override
    public int compareTo(Person o) {
      // TODO Auto-generated method stub
      if (this.age > o.getAge()) {
        return 1;
      } else if (this.age < o.getAge()) {
        return -1;
      }
      return age;
    }
  }
}

