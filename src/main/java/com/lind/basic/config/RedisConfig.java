package com.lind.basic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 对redis.
 */
@Configuration
public class RedisConfig {

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;

  /**
   * 统计bit位为1的总数.
   *
   * @param key .
   */
  public long bitCount(final String key) {
    return redisConnectionFactory.getConnection().bitCount(key.getBytes());
  }

  /**
   * redis重写RedisTemplate.
   */
  @Bean
  public RedisTemplate redisTemplate() {
    RedisTemplate redisTemplate = new RedisTemplate();
    RedisSerializer<String> stringSerializer = new StringRedisSerializer();
    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

    // redis key的序列化
    redisTemplate.setKeySerializer(stringSerializer);
    // redis value的序列化
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

    redisTemplate.setConnectionFactory(redisConnectionFactory);
    return redisTemplate;
  }
}
