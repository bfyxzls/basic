package com.lind.basic.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TopicPublisher {
    Logger logger = LoggerFactory.getLogger(TopicPublisher.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void publicTopic(String message) {
        try {
            //这两个routingKey都应该与test.basic.order.#配置，即同时会为test.basic.order发消息，消费者会收到两条消息
            for (String key : new String[]{"test.basic.order.add", "test.basic.order.modify"}) {
                MessageObj messageObj = new MessageObj(key, new Date());
                rabbitTemplate.convertAndSend(
                        TopicMqConfig.LIND_EXCHANGE,
                        key,
                        objectMapper.writeValueAsString(messageObj)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

