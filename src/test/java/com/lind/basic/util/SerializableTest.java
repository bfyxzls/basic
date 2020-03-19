package com.lind.basic.util;

import com.lind.basic.BaseTest;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class SerializableTest extends BaseTest {
  @Test
  public void read() throws Exception {
    Man person = new Man();
    person.setName("张三");
    person.setCode("001");
    List<Man> manList = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      manList.add(person);
    }


    // 持久化到磁盘,其中code不会被持久化
    ObjectOutputStream objectOutputStream =
        new ObjectOutputStream(new FileOutputStream("src/test/resources/output.txt"));
    objectOutputStream.writeObject(manList);
    objectOutputStream.close();

    // 从磁盘读出
    ObjectInputStream objectInputStream = new ObjectInputStream(
        new FileInputStream("src/test/resources/output.txt"));
    List<Man> manList1 = (List<Man>) objectInputStream.readObject();
    objectInputStream.close();
    System.out.println("name: " + manList1.size());

    // 读到json里
    String data = objectMapper.writeValueAsString(person);
    System.out.println("data: " + data);

  }

  static class Man implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    /**
     * 不会被持久化到磁盘.
     */
    private transient String code;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {

      this.code = code;
    }
  }

}

