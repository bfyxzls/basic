package com.lind.basic.util;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.junit.Assert;
import org.junit.Test;

public class CommonUtilsTest {
  @Test
  public void telphoneStar() {
    String str = "13521972117";
    Assert.assertEquals("135****2117", CommonUtils.replaceStar(str, "*"));
  }

  @Test
  public void convertTest() {
    Map<String, Object> map = ImmutableMap.of("name", "zzl", "id", 1);
    Person person = CommonUtils.toObject(map, Person.class);
    person = person.toBuilder().email("zzl@sina.com").build();

    System.out.println(person.toString());
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  @Builder(toBuilder = true)
  @ToString
  static class Person {
    private String name;
    private Integer id;
    private String email;
  }
}
