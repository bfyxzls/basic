package com.lind.basic.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailEventListener {
  @EventListener
  public void handleOrderEvent(OrderEvent event) {
    System.out.println("电子邮件的事件:" + event.getMsg());
  }
}