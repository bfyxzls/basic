package com.lind.basic.config;

import java.util.Collections;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * jedis实体的分布锁.
 */
@Component
public class JedisLock {
  // 分布式锁用到的常量
  private static final Long RELEASE_SUCCESS = 1L;
  private static final String LOCK_SUCCESS = "OK";
  private static final String SET_IF_NOT_EXIST = "NX";
  private static final String SET_WITH_EXPIRE_TIME = "EX";
  private static final Long LOCK_EXPIRE_TIME = 60L;
  private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then "
      + "return redis.call('del', KEYS[1]) else return 0 end";
  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  /**
   * 获取锁.
   * NX|XX,NX- Only set the name if it does not already exist.
   * XX- Only set the name if it already exist.
   * EX|PX, expire time units : EX = seconds ; PX = milliseconds.
   */
  public Boolean tryLock(String lockKey, String clientId) {
    return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
      Jedis jedis = (Jedis) redisConnection.getNativeConnection();
      String result = jedis.set(lockKey, clientId,
          SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, LOCK_EXPIRE_TIME);
      return LOCK_SUCCESS.equals(result);
    });
  }

  /**
   * 获取锁.
   *
   * @param lockKey       键
   * @param clientId      值
   * @param expireSeconds 超时时间秒
   * @return
   */
  public Boolean tryLock(String lockKey, String clientId, long expireSeconds) {
    return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
      Jedis jedis = (Jedis) redisConnection.getNativeConnection();
      String result = jedis.set(lockKey, clientId,
          SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireSeconds);
      return LOCK_SUCCESS.equals(result);
    });
  }

  /**
   * 释放锁.
   */
  public Boolean releaseLock(String lockKey, String clientId) {
    return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
      Jedis jedis = (Jedis) redisConnection.getNativeConnection();
      Object result = jedis.eval(RELEASE_LOCK_SCRIPT,
          Collections.singletonList(lockKey), Collections.singletonList(clientId));
      return RELEASE_SUCCESS.equals(result);
    });
  }
}
