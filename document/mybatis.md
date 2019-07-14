[返回目录](../README.md)

### 为实体添加导航属性
导航属性用来在上层显示，它并不是数据表的字段
>@TableField(exist = false) 表示不是数据表字段，如果是需要表字体，可以改为true，或者删除这个注解。

### mybatis建立和更新时间自动填充
[详细介绍](https://www.cnblogs.com/lori/p/10281976.html)
定义实体
```
@Getter
@Builder(toBuilder = true)
@ToString
public class UserInfo {
  private Long id;
  private String name;
  private String email;

  @CreatedOnFuncation
  private LocalDateTime createdOn;
  @UpdatedOnFuncation
  private LocalDateTime updatedOn;
}
```

### 实体里日期格式化
```
   /**
   * 创建时间.
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  protected LocalDateTime createTime;
```

### mybatis plus主键说明
```
 /**
   * 主键.
   */
  @TableId(name = "id",type = IdType.ID_WORKER)
  protected Long id;
```
> ID_WORKER:Long，64位数,高效有序ID生产黑科技(sequence) 算法，全局唯一
> UUID:String,32位数，随机数，全局唯一

###  逻辑删除
@TableLogic
```
  /**
   * 删除标识   0：未删除，1：已删除.
   */
  @TableLogic
  protected Integer delFlag = DEL_FLAG_NORMAL;

```
### 注册逻辑删除和分页等bean
```
@EnableTransactionManagement
@Configuration
@MapperScan("cn.lind.mapper")
public class MybatisPlusConfig {
  /**
   * 乐观锁插件.
   */
  @Bean
  public OptimisticLockerInterceptor optimisticLockerInterceptor() {
    return new OptimisticLockerInterceptor();
  }

  /**
   * 分页插件.
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }
  
  @Bean
  public ISqlInjector sqlInjector() {
    return new LogicSqlInjector();
  }
}

```