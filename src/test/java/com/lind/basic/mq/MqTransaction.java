package com.lind.basic.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * 事务级消息.
 */
@Component
public class MqTransaction {


    /**
     * 运行带有重试功能的队列消费者.
     *
     * @param message
     * @param channel
     * @throws InterruptedException
     * @throws IOException
     */
    public void runRepeatMq(Message message, Channel channel, Consumer consumer)
            throws InterruptedException, IOException {
        try {
            consumer.accept(null);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception ex) {
            Thread.sleep(2000);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

}
