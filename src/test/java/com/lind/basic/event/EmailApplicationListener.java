package com.lind.basic.event;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailApplicationListener {

  @EventListener
  public void handlerOrderEvent(OrderEvent eventObject) {
    logger.info("同步事件-电子邮件的事件：{},时间：{}", eventObject.getMsg(), LocalDateTime.now());
  }

  @EventListener
  public void handlerRegisterUserEvent(RegisterUserEvent eventObject) {
    logger.info("同步事件-电子邮件的事件：{},时间：{}", eventObject.getUsername(), LocalDateTime.now());
  }
}
