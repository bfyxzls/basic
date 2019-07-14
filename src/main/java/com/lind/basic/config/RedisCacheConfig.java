package com.lind.basic.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@EnableCaching
@Configuration
public class RedisCacheConfig {

  /**
   * config.
   *
   * @return
   */
  @Bean
  public RedisCacheConfiguration redisCacheConfiguration() {
    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
        new Jackson2JsonRedisSerializer(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);

    // 持久化到redis里为json，默认是二进制
    RedisSerializationContext.SerializationPair<Object> pair =
        RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer);

    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(300))
        .serializeValuesWith(pair);

    return redisCacheConfiguration;
  }

  /**
   * cacheManager.
   *
   * @param connectionFactory .
   * @return
   */
  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    //初始化一个RedisCacheWriter
    RedisCacheWriter redisCacheWriter =
        RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
    //初始化RedisCacheManager
    RedisCacheManager cacheManager =
        new RedisCacheManager(redisCacheWriter, redisCacheConfiguration());
    return cacheManager;
  }

}
