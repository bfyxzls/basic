package com.lind.basic.cache;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {
  static Map<String, String> dictionary = new HashMap<>();

  @CachePut(value = "commonService", key = "targetClass + #p0")
  @Override
  public void add(String data) {
    dictionary.put(data, LocalDateTime.now().toString());
  }

  @CacheEvict(value = "commonService", key = "targetClass + #p0")
  @Override
  public void delete(String data) {
    dictionary.remove(data);
  }

  @Cacheable(value = "commonService", key = "targetClass  + #p0")
  @Override
  public String get(String data) {
    System.out.println("进入缓存get方法");
    return dictionary.get(data);
  }

  @Override
  public Collection<String> getAll() {
    return dictionary.values();
  }
}
