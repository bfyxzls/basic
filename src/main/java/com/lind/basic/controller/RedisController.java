package com.lind.basic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lind.basic.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("redis")
public class RedisController {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("set")
    public String set() {
        Token token = Token.builder().credentials("ok").region("hello").build();
        redisTemplate.opsForValue().set("test:user", token);
        return "OK";
    }

    @GetMapping("get")
    public Token get() throws IOException {
        return objectMapper.readValue(redisTemplate.opsForValue().get("test:user").toString(), Token.class);
    }
}
