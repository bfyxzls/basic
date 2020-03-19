package com.lind.basic.mybatis;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
@ToString
@TableName("user_info")
public class UserInfo {
    @Id
    private Long id;
    private String name;
    private String email;
    @TableField("created_on")
    private Instant createOn;
    @TableField("updated_on")
    private Instant updatedOn;
    @TableField("is_delete")
    private Integer delete;
}