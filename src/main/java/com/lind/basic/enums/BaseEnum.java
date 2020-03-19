package com.lind.basic.enums;

/**
 * 带有值和描述的枚举基类.
 */
public interface BaseEnum<E extends Enum<?>, T> {

  /**
   * 返回枚举的值.
   *
   * @return
   */
  Integer getCode();

  /**
   * 返回枚举描述.
   *
   * @return
   */
  String getDescription();

}
