package com.lind.basic.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class SmsEventListener {
    @EventListener
    public void handler(OrderEvent eventObject) throws InterruptedException {
        logger.info("同步事件-短信的事件：{},时间：{}", eventObject.getMsg(), LocalDateTime.now());
    }
}
