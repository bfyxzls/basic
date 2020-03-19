package com.lind.basic.mock;

import com.lind.basic.util.RandomUtils;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;

/**
 * redis模拟.
 */
@ActiveProfiles("test")
@Configuration
public class RedisServerMock {

  private RedisServer redisServer;
  private String redisHost = "localhost";
  private int redisPort = RandomUtils.getRandomInt(58000, 60000);

  @Bean
  RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration =
        new RedisStandaloneConfiguration(redisHost, redisPort);
    RedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration);
    return factory;
  }

  @PostConstruct
  public void startRedis() throws IOException {
    redisServer = new RedisServer(redisPort);
    redisServer.start();
  }

  @PreDestroy
  public void stopRedis() {
    redisServer.stop();
  }
}
