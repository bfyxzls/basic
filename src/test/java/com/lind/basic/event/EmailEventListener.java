package com.lind.basic.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class EmailEventListener {
    @EventListener
    public void handleOrderEvent(OrderEvent event) throws InterruptedException {
        logger.info("同步事件-电子邮件的事件：{},时间：{}", event.getMsg(), LocalDateTime.now());
        Thread.sleep(1000);
    }
}