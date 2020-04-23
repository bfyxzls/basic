package com.lind.basic.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TopicSubscriber {

    /**
     * lindqueue.
     *
     * @param data .
     */
    @RabbitListener(queues = TopicMqConfig.LIND_QUEUE_NAME)
    public void lindQueue(String data) {
        logger.info("LIND_QUEUE_ROUTEKEY1收到消息:{}", data);

    }


}
