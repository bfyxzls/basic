package com.lind.basic.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

/**
 * 实体工具类.
 */
@Slf4j
public class EntityUtils {

  /**
   * 类属性复制工具方法.
   *
   * @param clazz  要创建的类class，也就是目标类的class
   * @param source 数据源对象
   * @param <T>    返回值类型
   * @return 泛型创建的对象
   */
  public static <T> T convert(Class<T> clazz, @NotNull Object source)
      throws RuntimeException {
    T target;
    try {
      target = clazz.newInstance();
      BeanUtils.copyProperties(source, target);
    } catch (Exception e) {
      logger.error("EntityUtils convert error.", e);
      throw new RuntimeException(e);
    }
    return target;
  }

  /**
   * 类属性复制工具方法,支持集合.
   *
   * @param clazz 要创建的类class，也就是目标类的class
   * @param list  数据源集合对象
   * @param <T>   返回值类型
   * @return 泛型创建的对象
   */
  public static <T> List<T> convert(Class<T> clazz, @NotNull List list)
      throws RuntimeException {
    try {
      return (List<T>) list.stream()
          .map(l -> convert(clazz, l))
          .filter(l -> l != null)
          .collect(Collectors.toList());
    } catch (Exception e) {
      logger.error("EntityUtils convert list error.", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * 类属性复制工具方法,新增使用.
   *
   * @param clazz  要创建的类class，也就是目标类的class
   * @param source 数据源对象
   * @param <T>    返回值类型
   * @return 泛型创建的对象
   */
  public static <T> T convertForSave(Class<T> clazz, @NotNull Object source) {
    T t = convert(
        clazz,
        source);
    Method initSaveMethod = ReflectionUtils.findMethod(clazz,
        "initSave");
    if (initSaveMethod != null) {
      ReflectionUtils.invokeMethod(initSaveMethod, t);
    }
    return t;
  }

  /**
   * 类属性复制工具方法,修改使用,对象和对象之间的对拷.
   *
   * @param t      目标对象
   * @param source 数据源对象
   * @return 拷贝后的对象返回，其实就是target
   */
  public static <T> T convertEntityToEntityForUpdate(@NotNull T t, @NotNull Object source) {
    BeanUtils.copyProperties(source, t);
    Method initUpdateMethod = ReflectionUtils.findMethod(t.getClass(),
        "initUpdate");
    if (initUpdateMethod != null) {
      ReflectionUtils.invokeMethod(initUpdateMethod, t);
    }
    return t;
  }

  /**
   * Map转成实体对象，注意类必须是public的.
   *
   * @param map   map实体对象包含属性
   * @param clazz 实体对象类型
   * @return
   */
  public static <T> T convertMapToObject(Map<String, Object> map, Class<T> clazz) {
    if (map == null) {
      return null;
    }
    T obj = null;
    try {
      obj = clazz.newInstance();

      Field[] fields = obj.getClass().getDeclaredFields();
      for (Field field : fields) {
        int mod = field.getModifiers();
        if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
          continue;
        }
        field.setAccessible(true);
        String filedTypeName = field.getType().getName();
        if (filedTypeName.equalsIgnoreCase("java.util.date")) {
          String datetimestamp = String.valueOf(map.get(field.getName()));
          if (datetimestamp.equalsIgnoreCase("null")) {
            field.set(obj, null);
          } else {
            field.set(obj, new Date(Long.parseLong(datetimestamp)));
          }
        } else {
          String v = map.get(field.getName()).toString();
          field.set(obj, map.get(field.getName()));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return obj;
  }
}
