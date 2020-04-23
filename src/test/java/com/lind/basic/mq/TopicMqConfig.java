package com.lind.basic.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TopicMqConfig {
    public static final String LIND_EXCHANGE = "test.basic.exchange";
    public static final String LIND_QUEUE_NAME = "test.basic.order";

    /**
     * 创建普通交换机.
     */
    @Bean
    public TopicExchange lindExchange() {
        return (TopicExchange) ExchangeBuilder.topicExchange(LIND_EXCHANGE).durable(true)
                .build();
    }


    /**
     * 绑定普通队列.
     *
     * @return
     */
    @Bean
    public Binding bindBuilders() {
        return BindingBuilder.bind(key1())
                .to(lindExchange())
                .with("test.basic.order.#");//绑定一个泛型的routingKey
    }

    @Bean
    public Queue key1() {
        return new Queue(LIND_QUEUE_NAME);
    }




}
