package com.lind.basic.mybatis;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
@ToString
@TableName("user_extension")
public class UserExtension {
    @Id
    private Long id;
    private Long userId;
    private String realName;
    private String remark;
}