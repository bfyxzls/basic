package com.lind.basic.util;

import com.lind.basic.enums.BaseEnum;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 枚举辅助类.
 */
public class EnumUtils {
  /**
   * 通过枚举code返回枚举对象.
   */
  public static <T extends Enum<?> & BaseEnum> T codeOf(
      Class<T> enumClass, Integer code) {
    T[] enumConstants = enumClass.getEnumConstants();
    return Stream.of(enumConstants)
        .filter(e -> Objects.equals(e.getCode(), code))
        .findFirst()
        .orElse(null);
  }

  /**
   * 通过枚举description返回枚举对象.
   */
  public static <T extends Enum<?> & BaseEnum> T descriptionOf(
      Class<T> enumClass, String description) {
    T[] enumConstants = enumClass.getEnumConstants();
    return Stream.of(enumConstants)
        .filter(e -> Objects.equals(e.getDescription(), description))
        .findFirst()
        .orElse(null);
  }

  /**
   * 通过枚举name返回枚举对象.
   */
  public static <T extends Enum<?> & BaseEnum> T nameOf(
      Class<T> enumClass, String name) {
    T[] enumConstants = enumClass.getEnumConstants();
    return Stream.of(enumConstants)
        .filter(e -> Objects.equals(e.name(), name))
        .findFirst()
        .orElse(null);
  }
}
