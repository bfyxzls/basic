package com.lind.basic.mock;

import com.github.fridujo.rabbitmq.mock.compatibility.MockConnectionFactoryFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

/**
 * 模拟rabbitmq.
 */
@ActiveProfiles("test")
@Component
public class RabbitMqMock {
  @Bean
  public ConnectionFactory connectionFactory() {
    return new CachingConnectionFactory(MockConnectionFactoryFactory.build());
  }
}