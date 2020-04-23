package com.lind.basic.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 广播消息.
 */
@Component
public class FanoutPublisher {
    public static final String LIND_FANOUT_EXCHANGE = "test.basic.fanout";
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 广播交换机.
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(LIND_FANOUT_EXCHANGE);
    }

    @Bean
    public Queue product1Queue() {
        return new Queue("product1.queue");
    }

    @Bean
    public Queue product2Queue() {
        return new Queue("product2.queue");
    }

    @Bean
    public Binding product1QueueBinding() {
        return BindingBuilder.bind(product1Queue()).to(fanoutExchange());
    }

    @Bean
    public Binding product2QueueBinding() {
        return BindingBuilder.bind(product2Queue()).to(fanoutExchange());
    }

    /**
     * 发布广播消息.
     *
     * @param message .
     */
    public void fanoutPublish(String message) {
        try {
            rabbitTemplate.convertAndSend(LIND_FANOUT_EXCHANGE, null, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
