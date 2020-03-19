[返回目录](../README.md)

### 设计一个枚举基类和工具类
1. 基类枚举支持值和描述信息
2. 工具类支持根据值，名称和描述信息获取枚举对象

#### 枚举基类设计
```$xslt
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
```
> 上面定义的基类枚举，为它的子类枚举规定要实现`getCode`和`getDescription`两个方法.
#### 设置一个枚举工具类
```$xslt
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

```
> 这个工具类有三个方法，输入参数要求是个枚举，并且要求从EnumBase派生出来的，提供了三个返回枚举
值的方法，分别为值返回枚举，名称返回枚举，描述信息返回枚举等。

#### 定义自己的枚举对象
```$xslt
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

  @Override
  public Integer getCode() {
    return code;
  }

  @Override
  public String getDescription() {
    return description;
  }

}

```

#### 在单元测试里验证工厂类的方法
```$xslt
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

```
> 我们做为架构师，在写公用组件时，一定要写测试用例，以此来保证代码的正确性。