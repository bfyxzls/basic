package com.lind.basic.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
@Slf4j
public class DealLetterSubscriber {
  public static final String LIND_DL_EXCHANGE = "test.basic.dl.exchange";
  public static final String LIND_DEAD_QUEUE = "test.basic.queue.dead";

  /**
   * 创建死信交换机.
   */
  @Bean
  public TopicExchange lindExchangeDl() {
    return (TopicExchange) ExchangeBuilder.topicExchange(LIND_DL_EXCHANGE).durable(true)
        .build();
  }

  /**
   * 创建死信队列.
   */
  @Bean
  public Queue lindDelayQueue() {
    return QueueBuilder.durable(LIND_DEAD_QUEUE).build();
  }

  /**
   * 绑定死信队列.
   */
  @Bean
  public Binding bindDeadBuilders() {
    return BindingBuilder.bind(lindDelayQueue())
        .to(lindExchangeDl())
        .with(LIND_DEAD_QUEUE);
  }

  /**
   * subscriber.
   *
   * @param data .
   */
  @RabbitListener(queues = LIND_DEAD_QUEUE)
  public void dealLetter(String data) {
    try {
      logger.info("消费死信消息 ：{}", data);
    } catch (Exception ex) {
      logger.error("消费死信消息异常", ex);
    }
  }

}
