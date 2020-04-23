package com.lind.basic.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FanoutSubscriber {
    @RabbitListener(queues = "product1.queue")
    public void product1QueueRabbitListener(String data) {
        logger.info("product1QueueRabbitListener:{}", data);

    }

    @RabbitListener(queues = "product2.queue")
    public void product2QueueRabbitListener(String data) {
        logger.info("product2QueueRabbitListener:{}", data);

    }
}
