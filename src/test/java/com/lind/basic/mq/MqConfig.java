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
  public static final String LIND_DL_EXCHANGE = "test.basic.dl.exchange";
  public static final String LIND_QUEUE = "test.basic.cold.queue";
  public static final String LIND_DEAD_QUEUE = "test.basic.queue.dead";
  public static final String LIND_FANOUT_EXCHANGE = "test.basic.fanoutExchange";
  public static final String LIND_QUEUE_ROUTEKEY = "test.basic.*";
  public static final String LIND_QUEUE_ROUTEKEY1 = "test.basic.a1";
  public static final String LIND_QUEUE_ROUTEKEY2 = "test.basic.a2";
  public static final String LIND_QUEUE_Long = "test.dudu.long";

  /**
   * 单位为微秒.
   */
  private static final long makeCallExpire = 60000;

  /**
   * 创建普通交换机.
   */
  @Bean
  public TopicExchange lindExchange() {
    return (TopicExchange) ExchangeBuilder.topicExchange(LIND_EXCHANGE).durable(true)
        .build();
  }

  /**
   * 创建死信交换机.
   */
  @Bean
  public TopicExchange lindExchangeDl() {
    return (TopicExchange) ExchangeBuilder.topicExchange(LIND_DL_EXCHANGE).durable(true)
        .build();
  }

  /**
   * 创建普通队列.
   */
  @Bean
  public Queue lindQueue() {
    return QueueBuilder.durable(LIND_QUEUE)
        .withArgument("x-dead-letter-exchange", LIND_DL_EXCHANGE)//设置死信交换机
        .withArgument("x-message-ttl", makeCallExpire)
        .withArgument("x-dead-letter-routing-key", LIND_DEAD_QUEUE)//设置死信routingKey
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
   * 绑定普通队列.
   *
   * @return
   */
  @Bean
  public Binding bindBuilders() {
    return BindingBuilder.bind(lindQueue())
        .to(lindExchange())
        .with(LIND_QUEUE);
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
