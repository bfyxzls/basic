package com.lind.basic.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.apache.commons.lang3.StringUtils;


/**
 * 日期工具类.
 *
 * @author liuyutao
 * @create 2018-07-10 14:46
 */
public class DateUtils {

  /**
   * 默认日期格式化方式.
   */
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  /**
   * 将字符串类型日期转换Date.
   *
   * @param date 字符串日期
   */
  public static LocalDateTime getDateFormat(String date) {
    if (StringUtils.isBlank(date)) {
      throw new IllegalArgumentException("日期不能为空");
    }
    try {
      DateTimeFormatter df = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
      LocalDateTime ldt = LocalDateTime.parse(date, df);
      return ldt;
    } catch (DateTimeParseException e) {
      DateTimeFormatter df = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
      LocalDateTime dt = LocalDateTime.parse(date);
      LocalDateTime ldt = LocalDateTime.parse(getDateFormat(dt), df);
      return ldt;
    } catch (Exception e) {
      throw new IllegalArgumentException("字符串日期转换date失败");
    }
  }

  /**
   * 将Date转换字符串类型日期.
   *
   * @param date 字符串日期
   */
  public static String getDateFormat(LocalDateTime date) {
    if (date == null) {
      throw new IllegalArgumentException("日期不能为空");
    }
    try {
      DateTimeFormatter df = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
      String localTime = df.format(date);
      return localTime;
    } catch (Exception e) {
      throw new IllegalArgumentException("LocalDateTime转换字符串类型日期失败");
    }
  }
}
