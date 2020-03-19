package com.lind.basic.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lind.basic.util.EnumUtils;

/**
 * lind框架系统状态.
 */
public enum LindStatus implements BaseEnum {
  INIT(1, "初始化"),
  NORMAL(2, "正常");

  private Integer code;
  private String description;

  LindStatus(Integer code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Json反序列化在把code数值转为枚举元素时，调用这个方法.
   *
   * @param code .
   * @return
   */
  @JsonCreator
  public static LindStatus jsonCreate(Integer code) {
    return EnumUtils.codeOf(LindStatus.class, code);
  }

  @Override
  public Integer getCode() {
    return code;
  }

  @Override
  public String getDescription() {
    return description;
  }

}
