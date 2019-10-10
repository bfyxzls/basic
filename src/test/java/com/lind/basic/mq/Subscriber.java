package com.lind.basic.mq;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Subscriber {


  /**
   * lindqueue.
   *
   * @param data .
   */
  @RabbitListener(queues = MqConfig.LIND_QUEUE_ROUTEKEY1)
  public void lindQueue(String data) {
    if (LocalDateTime.now().getSecond() != 15) {
      throw new IllegalArgumentException("故意抛出来的异常1!");
    } else {
      logger.info("LIND_QUEUE从队列拿到数据 ：{}", data);
    }
  }

  /**
   * cold queue.
   *
   * @param data .
   */
  @RabbitListener(queues = MqConfig.LIND_QUEUE_ROUTEKEY2)
  public void lindQueue2(String data) {
    if (LocalDateTime.now().getSecond() != 20) {
      throw new IllegalArgumentException("故意抛出来的异常2!");
    } else {
      logger.info("LIND_QUEUE2从队列拿到数据 ：{}", data);
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


  /**
   * 耗时的任务.
   *
   * @param data .
   * @throws Exception .
   */
  @RabbitListener(queues = MqConfig.LIND_QUEUE_Long)
  public void longJob(String data) throws Exception {
    System.out.println(data);
    Thread.sleep(1000);
  }
}
