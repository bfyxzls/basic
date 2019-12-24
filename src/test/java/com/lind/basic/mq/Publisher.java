package com.lind.basic.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
  Logger logger = LoggerFactory.getLogger(Publisher.class);
  @Autowired
  private RabbitTemplate rabbitTemplate;
  @Autowired
  private ObjectMapper objectMapper;

  public void publishZzl(String message) {
    try {
      MessageObj messageObj = new MessageObj(message, new Date());
      rabbitTemplate.convertAndSend(
          MqBase.EXCHANGE,
          "zzl.hello2",
          objectMapper.writeValueAsString(messageObj)
      );
      logger.info("Publisher:{}", messageObj);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 消息异常消息，它会反复重试消费，直到成功.
   */
  public void publish(String message) {
    try {
      rabbitTemplate
          .convertAndSend(MqConfig.LIND_EXCHANGE, MqConfig.LIND_QUEUE_ROUTEKEY,
              message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 测试死信消息.
   *
   * @param message .
   */
  public void publishDealLetter(String message) {
    try {
      rabbitTemplate
          .convertAndSend(MqConfig.LIND_EXCHANGE, MqConfig.LIND_TEST_DEAL_QUEUE,
              message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 发布广播消息.
   *
   * @param message .
   */
  public void fanoutPublish(String message) {
    try {
      rabbitTemplate.convertAndSend(MqConfig.LIND_FANOUT_EXCHANGE, null, "广播消息");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void longJobPublish(String message) {
    rabbitTemplate
        .convertAndSend(MqConfig.LIND_EXCHANGE, MqConfig.LIND_QUEUE_Long,
            message);
  }
}

