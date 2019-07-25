package com.lind.basic.util;

import com.google.common.collect.ImmutableMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest()
@Profile("test")
public class CommonUtilsTest {
  @Value("${author}")
  private String val;

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

  @Test
  public void localDateTest() {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM");
    System.out.println(LocalDate.now().format(df));
  }

  @Test
  public void logger() {
    logger.info("hello");
  }

  @Test
  public void config() {
    logger.info(val);
  }

  @Test
  public void convertIntArray() {
    String hello = "1,2,3,4";
    String[] helloArray = StringUtils.split(hello, ",");
    Integer[] intArray = CommonUtils.toIntegerArray(helloArray);
    for (Integer i : intArray) {
      System.out.println("i=" + i);
    }
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
