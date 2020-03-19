[返回目录](../README.md)

### jpa实体基类和继承
[详细介绍](https://www.cnblogs.com/lori/p/10266508.html)

```
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class EntityBase {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;


  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @Column(name = "created_on")
  protected LocalDateTime createdOn;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @Column(name = "updated_on")
  protected LocalDateTime updatedOn;

  /**
   * Sets createdAt before insert
   */
  @PrePersist
  public void setCreationDate() {
    this.createdOn = LocalDateTime.now();
    this.updatedOn = LocalDateTime.now();
  }

  /**
   * Sets updatedAt before update
   */
  @PreUpdate
  public void setChangeDate() {
    this.updatedOn = LocalDateTime.now();
  }
}

// 具体类，继承这个基类
@Entity
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class TestEntityBuilder extends EntityBase {
  private String title;
  private String description;

  @Builder(toBuilder = true)
  public TestEntityBuilder(Long id, LocalDateTime createdOn, LocalDateTime updatedOn,
                           String title, String description) {
    super(id, createdOn, updatedOn);
    this.title = title;
    this.description = description;
  }
}
```