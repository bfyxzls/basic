package com.lind.basic.mq;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.function.Consumer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MqBase {
  public final static String EXCHANGE = "test.exchange";
  public final static String QUEUE_DELAY = "hello.world.delay2";

  public static final String LIND_DL_EXCHANGE = "test.dl.exchange2";
  public static final String LIND_DEAD_QUEUE = "test.queue.dead2";

  /**
   * 添加死信队列的支持.
   *
   * @param queueName .
   * @return
   */
  public Queue createQueueWithDead(String queueName, Integer ttl) {
    if (ttl == null) {
      ttl = 10000;
    }
    return QueueBuilder.durable(queueName)
        .withArgument("x-dead-letter-exchange", LIND_DL_EXCHANGE)
        .withArgument("x-message-ttl", ttl)
        .withArgument("x-dead-letter-routing-key", LIND_DEAD_QUEUE)
        .build();
  }

  public Queue createQueueWithDead(String queueName) {
    return createQueueWithDead(queueName, null);
  }

  /**
   * 运行带有重试功能的队列消费者.
   *
   * @param message
   * @param channel
   * @throws InterruptedException
   * @throws IOException
   */
  protected void runRepeatMq(Message message, Channel channel, Consumer consumer)
      throws InterruptedException, IOException {
    try {
      consumer.accept(null);
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    } catch (Exception ex) {
      Thread.sleep(2000);
      channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
    }
  }

  /**
   * 创建普通交换机.
   */
  @Bean
  public TopicExchange lindExchange() {
    //消息持久化
    return (TopicExchange) ExchangeBuilder.topicExchange(EXCHANGE).durable(true).build();
  }

  @Bean
  public TopicExchange deadExchange() {
    return (TopicExchange) ExchangeBuilder.topicExchange(LIND_DL_EXCHANGE).durable(true).build();
  }

  @Bean
  public Queue testDelayQueue() {
    return QueueBuilder.durable(QUEUE_DELAY)
        .withArgument("x-dead-letter-exchange", LIND_DL_EXCHANGE)
        .withArgument("x-message-ttl", 10000)
        .withArgument("x-dead-letter-routing-key", LIND_DEAD_QUEUE)
        .build();
  }

  @Bean
  public Queue deadQueue() {
    return new Queue(LIND_DEAD_QUEUE);
  }

  @Bean
  public Binding bindDelayBuildersRouteKey() {
    return BindingBuilder.bind(testDelayQueue()).to(lindExchange()).with(QUEUE_DELAY);
  }

  @Bean
  public Binding bindDeadBuildersRouteKey() {
    return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(LIND_DEAD_QUEUE);
  }

}
