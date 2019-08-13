package com.lind.basic.mq;

import com.lind.basic.BaseTest;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class MqTest extends BaseTest {
  @Autowired
  Publisher publisher;

  /**
   * 将会把消息发送给订阅了publisher.routekey的所有消费者.
   *
   * @throws Exception .
   */
  @Test
  public void publisherTest() throws Exception {
    publisher.publish("hello lind");
    TimeUnit.MILLISECONDS.sleep(6000);//耗时3分钟

  }
}
