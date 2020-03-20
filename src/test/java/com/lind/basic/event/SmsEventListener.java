package com.lind.basic.event;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsEventListener implements ApplicationListener<OrderEvent> {
  @Override
  @EventListener
  public void handler(OrderEvent eventObject) throws InterruptedException {
    logger.info("同步事件-短信的事件：{},时间：{}", eventObject.getMsg(), LocalDateTime.now());
  }
}
