package com.lind.basic.iocbean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class InitDestroy {
  @PostConstruct
  public void initPostConstruct() {
    System.out.println("执行PostConstruct注解标注的方法");
  }

  @PreDestroy
  public void preDestroy() {
    System.out.println("执行preDestroy注解标注的方法");
  }

  public void print() {
    System.out.println("打印");
  }
}
