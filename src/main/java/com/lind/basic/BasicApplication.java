package com.lind.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BasicApplication {

  static int hello() {
    int a = 1;
    int b = 2;
    int c = a + b;
    return c;
  }

  public static void main(String[] args) throws Exception {
    System.out.println(hello());
    SpringApplication.run(BasicApplication.class, args);
  }
}