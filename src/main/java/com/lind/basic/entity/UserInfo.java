package com.lind.basic.entity;


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
    @TableField("create_on")
    private String createOn;
    @TableField("update_on")
    private String updatedOn;
    @TableField("is_delete")
    private Integer delete;
    private Instant createTime;
    private Instant updateTime;
    private String tenantId;
}