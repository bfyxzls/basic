package com.lind.basic.controller;

import com.lind.basic.entity.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("欢迎接口")
public class HelloControler {
  @ApiOperation(value = "获取列表", notes = "获取用户欢迎列表")
  @GetMapping(value = "/hello")
  public ResponseEntity<Token> get() {
    byte[] a1 = new byte[1024 * 1024];
    return ResponseEntity.ok(Token.builder().build());
  }

  @ApiOperation(value = "获取欢迎词条", notes = "获取一个欢迎记录")
  @GetMapping(value = "/hello/{id}")
  public ResponseEntity<Token> get(@ApiParam("编号") @PathVariable int id) {
    return ResponseEntity.ok(Token.builder().build());
  }

  @ApiOperation(value = "添加欢迎词条", notes = "添加一个欢迎记录")
  @PostMapping(value = "/hello")
  public ResponseEntity<Token> post(@ApiParam("实体") @RequestBody Token token) {
    return null;
  }

  @ApiOperation(value = "更新欢迎词条", notes = "更新一个欢迎记录")
  @PutMapping(value = "/hello/{id}")
  public ResponseEntity<Token> put(@ApiParam("编号") @PathVariable int id,
                                   @ApiParam("实体") @RequestBody Token token) {
    return null;
  }

  @ApiOperation(value = "删除欢迎词条", notes = "删除一个欢迎记录")
  @DeleteMapping(value = "/hello/{id}")
  public ResponseEntity<Token> delete(@ApiParam("编号") @PathVariable int id) {
    return null;
  }
}
