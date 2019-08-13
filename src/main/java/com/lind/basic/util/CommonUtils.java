package com.lind.basic.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

/**
 * 公用类库.
 */
public class CommonUtils {

  /**
   * Suppress default constructor for noninstantiability.
   */
  private CommonUtils() {
    throw new AssertionError();
  }

  /**
   * md5 加密.
   *
   * @return
   */
  public static String md5(String str) {
    return md5(str, true);
  }

  /**
   * md5 加密.
   *
   * @return
   */
  public static String md5(String str, boolean isUpper) {
    char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F'};
    try {
      byte[] btInput = str.getBytes("GBK");
      // 获得MD5摘要算法的 MessageDigest 对象
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      // 使用指定的字节更新摘要
      mdInst.update(btInput);
      // 获得密文
      byte[] md = mdInst.digest();
      // 把密文转换成十六进制的字符串形式
      int j = md.length;
      char[] strs = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        strs[k++] = hexDigits[byte0 >>> 4 & 0xf];
        strs[k++] = hexDigits[byte0 & 0xf];
      }

      return isUpper
          ? new String(strs).toUpperCase()
          : new String(strs).toLowerCase();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 获取当前时间秒数.
   *
   * @return
   */
  public static String date2utcStr() {
    long miliSeconds = new Date().getTime();
    return String.valueOf(miliSeconds / 1000L);
  }

  /**
   * 根据当前字符串获取毫秒数.
   *
   * @return
   */
  public static String date2utcStr(String date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Date d = sdf.parse(date);
      long dateTime = d.getTime();
      return String.valueOf(dateTime / 1000L);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "error";
  }

  /**
   * 毫秒数转 LocalDateTime.
   *
   * @return
   */
  public static LocalDateTime instant2LocalDateTime(Long time) {
    if (time == null || time == 0) {
      return LocalDateTime.now();
    }
    Date date = new Date(time * 1000);
    Instant instant = date.toInstant();
    ZoneId zone = ZoneId.systemDefault();
    return LocalDateTime.ofInstant(instant, zone);
  }

  /**
   * 如果传入的时间为null，则默认为最小时间.
   * 1970-01-01
   *
   * @return
   */
  public static LocalDateTime ifNullToMinTime(Long epochMilli) {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    if (epochMilli == null) {
      return LocalDateTime.parse("1970-01-01 00:00:00", df);
    }
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault());
  }

  /**
   * 如果传入的时间为null,则默认为最大时间.
   *
   * @return
   */
  public static LocalDateTime ifNullToMaxTime(Long epochMilli) {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    if (epochMilli == null) {
      return LocalDateTime.parse("2999-01-01 00:00:00", df);
    }
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault());
  }

  /**
   * 默认字符串.
   *
   * @return
   */
  public static String stringNulltoDefault(String value) {
    if (value == null) {
      return "";
    }
    return value;
  }

  /**
   * 得到一个随机字符串.
   *
   * @return
   */
  public static String getRandomString() {
    return UUID.randomUUID().toString();
  }


  /**
   * 拆分集合.
   *
   * @param <T>     .
   * @param resList 要拆分的集合
   * @param count   每个集合的元素个数
   * @return 返回拆分后的各个集合
   */
  public static <T> List<List<T>> split(List<T> resList, int count) {

    if (resList == null || count < 1) {
      return null;
    }
    List<List<T>> ret = new ArrayList<>();
    int size = resList.size();
    if (size <= count) { //数据量不足count指定的大小
      ret.add(resList);
    } else {
      int pre = size / count;
      int last = size % count;
      //前面pre个集合，每个大小都是count个元素
      for (int i = 0; i < pre; i++) {
        List<T> itemList = new ArrayList<T>();
        for (int j = 0; j < count; j++) {
          itemList.add(resList.get(i * count + j));
        }
        ret.add(itemList);
      }
      //last的进行处理
      if (last > 0) {
        List<T> itemList = new ArrayList<T>();
        for (int i = 0; i < last; i++) {
          itemList.add(resList.get(pre * count + i));
        }
        ret.add(itemList);
      }
    }
    return ret;

  }

  /**
   * 实际替换动作.
   *
   * @param param .
   * @return
   */
  public static String replaceStar(String param, String placeholder) {
    if (param == null) {
      return "";
    }
    String userNameAfterReplaced = "";
    int nameLength = param.length();
    if (nameLength < 3 && nameLength > 0) {
      userNameAfterReplaced = param;
    } else {
      int num1;
      int num2;
      int num3;
      num2 = (int) Math.ceil(nameLength / 3.0);
      num1 = (int) Math.floor(nameLength / 3.0);
      num3 = nameLength - num1 - num2;
      String star = StringUtils.repeat(placeholder, num2);
      userNameAfterReplaced =
          param.replaceAll(
              "(.{" + num1 + "})(.{" + num2 + "})(.{" + num3 + "})", "$1" + star + "$3");
    }
    return userNameAfterReplaced;
  }

  /**
   * 将对象转为map.
   *
   * @param object .
   * @return
   */
  public static Map<String, Object> toMap(Object object) {
    return JSON.parseObject(
        JSON.toJSONString(object),
        new TypeReference<Map<String, Object>>() {
        });
  }

  /**
   * 将对象转为map.
   *
   * @param object .
   * @return
   */
  public static <T> T toObject(Object object, Class<T> type) {
    return JSON.parseObject(
        JSON.toJSONString(object), type);
  }

  /**
   * 时间戳转日期格式.
   *
   * @param seconds .
   * @param format  .
   * @return
   */
  public static String timeStamp2Date(Long seconds, String format) {
    if (seconds == null || seconds.equals(0L)) {
      return "";
    }

    if (org.apache.commons.lang.StringUtils.isEmpty(format)) {
      format = "yyyy-MM-dd'T'HH:mm:ss";
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(new Date(seconds));
  }

  /**
   * 四舍五入.
   *
   * @param value .
   * @param digit .
   * @return
   */
  public static String round(double value, int digit) {
    NumberFormat nf = NumberFormat.getNumberInstance();
    // 保留两位小数
    nf.setMaximumFractionDigits(digit);
    nf.setRoundingMode(RoundingMode.DOWN);
    return nf.format(value);

  }

  /**
   * 字符数组转Long数组.
   *
   * @param param .
   * @return
   */
  public static Long[] toLongArray(String[] param) {
    Long[] num = new Long[param.length];
    for (int idx = 0; idx < param.length; idx++) {
      num[idx] = Long.parseLong(param[idx]);
    }
    return num;
  }

  /**
   * 字符数组转Integer数组.
   *
   * @param param .
   * @return
   */
  public static Integer[] toIntegerArray(String[] param) {
    Integer[] num = new Integer[param.length];
    for (int idx = 0; idx < param.length; idx++) {
      num[idx] = Integer.parseInt(param[idx]);
    }
    return num;
  }

  /**
   * 长型型转整型.
   *
   * @param val .
   * @return
   */
  public static Integer toInteger(Long val) {
    return Integer.parseInt(val.toString());
  }
}
