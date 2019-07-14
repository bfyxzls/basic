package com.lind.basic.mq;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Subscriber {

  /**
   * subscriber.
   *
   * @param data .
   */
  @RabbitListener(queues = MqConfig.LIND_DEAD_QUEUE)
  public void customerSign(String data) {
    try {

      logger.info("从队列拿到数据 ：{}", data);

    } catch (Exception ex) {
      logger.error("签约同步异常", ex);
    }
  }

  /**
   * lindqueue.
   *
   * @param data .
   */
  @RabbitListener(queues = MqConfig.LIND_QUEUE_ROUTEKEY1)
  public void lindQueue(String data) {
    try {
      logger.info("LIND_QUEUE从队列拿到数据 ：{}", data);
      TimeUnit.MILLISECONDS.sleep(5);

    } catch (Exception ex) {
      logger.error("LIND_QUEUE异常", ex);
    }
  }

  /**
   * cold queue.
   *
   * @param data .
   */
  @RabbitListener(queues = MqConfig.LIND_QUEUE_ROUTEKEY2)
  public void lindQueue2(String data) {
    try {
      logger.info("LIND_QUEUE2从队列拿到数据 ：{}", data);
      TimeUnit.MILLISECONDS.sleep(5);

    } catch (Exception ex) {
      logger.error("LIND_QUEUE2异常", ex);
    }
  }

  @RabbitListener(queues = "product1.queue")
  public void product1(String data) {
    System.out.println(data);
  }

  @RabbitListener(queues = "product2.queue")
  public void product2(String data) {
    System.out.println(data);
  }
}
