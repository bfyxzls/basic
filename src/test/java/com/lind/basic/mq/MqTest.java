package com.lind.basic.mq;

import com.lind.basic.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

@ActiveProfiles("test")
public class MqTest extends BaseTest {
    @Autowired
    TopicPublisher topicPublisher;
    @Autowired
    FanoutPublisher fanoutPublisher;

    /**
     * 将会把消息发送给订阅了publisher.routekey的所有消费者.
     *
     * @throws Exception .
     */
    @Test
    public void topicTest() throws Exception {
        topicPublisher.publicTopic("hello lind");
        TimeUnit.MILLISECONDS.sleep(1000);
    }

    @Test
    public void fanoutTest() throws InterruptedException {
        fanoutPublisher.fanoutPublish("hello fanout");
        TimeUnit.MILLISECONDS.sleep(1000);
    }
}
