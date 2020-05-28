package com.lind.basic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lind.basic.entity.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("redis")
public class RedisController {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("set")
    public ResponseEntity set() throws JsonProcessingException {
        Token token = Token.builder()
                .credentials("ok")
                .region("hello")
                .build();
        redisTemplate.opsForValue().set("test:user", token);//redisTemplate帮我们序列化
        redisTemplate.opsForHash().put("author", "zzl", token);
        redisTemplate.opsForList().leftPush("star", token);
        redisTemplate.opsForList().leftPush("star", "1-star");
        return ResponseEntity.ok("OK");
    }

    @GetMapping("get")
    public ResponseEntity get() throws IOException {
        return ResponseEntity.ok((Token) redisTemplate.opsForValue().get("test:user"));
    }

    @GetMapping("get-hash")
    public ResponseEntity getHash() throws IOException {
        return ResponseEntity.ok((Token) redisTemplate.opsForHash().get("author", "zzl"));
    }

    @GetMapping("get-list")
    public ResponseEntity getList() throws IOException {
        return ResponseEntity.ok(redisTemplate.opsForList().leftPop("star"));
    }

}
