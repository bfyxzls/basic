package com.lind.basic.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lind.basic.enums.BaseEnum;
import com.lind.basic.enums.LindStatus;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class EnumTest {
  @Test
  public void codeTest() {
    Assert.assertEquals("SMALL_SCALE", EnumPayTaxesNature.getValue("1").name());
  }

  @Test
  public void textTest() {
    Assert.assertEquals("小规模", EnumPayTaxesNature.getValue("1").getDescription());
  }

  @Test
  public void contions() {
    for (EnumPayTaxesNature s : EnumPayTaxesNature.values()) {
      System.out.println(s.code + ",");
    }
    System.out.println("contion:" + (Arrays.stream(EnumPayTaxesNature.values())
        .map(i -> i.code).filter(i -> i.equals(-1)).count() > 0) + "");
  }

  @Test
  public void baseEnumTest() {
    Assert.assertEquals("NORMAL", LindStatus.NORMAL.name());
    // 枚举code返回枚举对象
    Assert.assertEquals(LindStatus.NORMAL, EnumUtils.codeOf(LindStatus.class, 2));
    // 枚举描述返回枚举对象
    Assert.assertEquals(LindStatus.NORMAL, EnumUtils.descriptionOf(LindStatus.class, "正常"));
    // 枚举name返回枚举对象
    Assert.assertEquals(LindStatus.NORMAL, EnumUtils.nameOf(LindStatus.class, "NORMAL"));
  }

  enum EnumPayTaxesNature implements BaseEnum {

    OTHER(0, "其他"), SMALL_SCALE(1, "小规模"), GENERALLY(2, "一般人");
    private Integer code;
    private String text;

    EnumPayTaxesNature(Integer code, String text) {
      this.code = code;
      this.text = text;
    }

    @JsonCreator
    public static EnumPayTaxesNature getValue(String code) {
      return EnumUtils.codeOf(EnumPayTaxesNature.class, Integer.parseInt(code));
    }

    @Override
    public Integer getCode() {
      return this.code;
    }

    @Override
    public String getDescription() {
      return this.text;
    }

    @JsonValue
    public String getCodeStr() {
      return this.code + "";
    }
  }
}
