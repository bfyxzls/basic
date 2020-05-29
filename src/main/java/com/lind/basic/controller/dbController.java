package com.lind.basic.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lind.basic.entity.UserInfo;
import com.lind.basic.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class dbController {
    @Autowired
    UserMapper userMapper;

    @GetMapping("db")
    public ResponseEntity db() {
        EntityWrapper<UserInfo> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("name", "zhz修改了修改了");
        return ResponseEntity.ok(userMapper.selectList(entityWrapper));
    }
}
