package com.lind.basic.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class MqConfig {
  public static final String LIND_EXCHANGE = "test.basic.exchange";
  public static final String LIND_TEST_DEAL_QUEUE = "test.basic.cold.queue";
  public static final String LIND_FANOUT_EXCHANGE = "test.basic.fanoutExchange";
  public static final String LIND_QUEUE_ROUTEKEY = "test.basic.*";
  public static final String LIND_QUEUE_ROUTEKEY1 = "test.basic.a1";
  public static final String LIND_QUEUE_ROUTEKEY2 = "test.basic.a2";
  public static final String LIND_QUEUE_Long = "test.dudu.long";

  /**
   * 单位为毫秒.
   */
  private static final long makeCallExpire = 1000;

  /**
   * 创建普通交换机.
   */
  @Bean
  public TopicExchange lindExchange() {
    return (TopicExchange) ExchangeBuilder.topicExchange(LIND_EXCHANGE).durable(true)
        .build();
  }


  /**
   * 创建普通队列.
   */
  @Bean
  public Queue lindNeedDealLetterQueue() {
    return QueueBuilder.durable(LIND_TEST_DEAL_QUEUE)
        .withArgument("x-dead-letter-exchange", DealLetterSubscriber.LIND_DL_EXCHANGE)//设置死信交换机
        .withArgument("x-message-ttl", makeCallExpire)
        .withArgument("x-dead-letter-routing-key", DealLetterSubscriber.LIND_DEAD_QUEUE)//设置死信routingKey
        .build();
  }


  /**
   * 绑定普通队列.
   *
   * @return
   */
  @Bean
  public Binding bindBuilders() {
    return BindingBuilder.bind(lindNeedDealLetterQueue())
        .to(lindExchange())
        .with(LIND_TEST_DEAL_QUEUE);
  }


  @Bean
  public Queue key1() {
    return new Queue(LIND_QUEUE_ROUTEKEY1);
  }

  @Bean
  public Queue key2() {
    return new Queue(LIND_QUEUE_ROUTEKEY2);
  }

  /**
   * 绑定了routekey，一个routekey可以被多个队列绑定，类似于广播.
   *
   * @return
   */
  @Bean
  public Binding bindBuildersRouteKey1() {
    return BindingBuilder.bind(key1())
        .to(lindExchange())
        .with(LIND_QUEUE_ROUTEKEY);
  }

  /**
   * bind.
   *
   * @return
   */
  @Bean
  public Binding bindBuildersRouteKey2() {
    return BindingBuilder.bind(key2())
        .to(lindExchange())
        .with(LIND_QUEUE_ROUTEKEY);
  }

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

  @Bean
  public Queue longQueue() {
    return new Queue(LIND_QUEUE_Long);
  }

  @Bean
  public Binding bindLongQueue() {
    return BindingBuilder.bind(longQueue())
        .to(lindExchange())
        .with(LIND_QUEUE_Long);
  }
}
