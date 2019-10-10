package com.lind.basic.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SmsEventListener {
  @EventListener
  public void handleOrderEvent(OrderEvent event) {
    System.out.println("短信的事件:" + event.getMsg());
  }
}