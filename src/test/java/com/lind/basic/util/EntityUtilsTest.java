package com.lind.basic.util;

import java.util.HashMap;
import java.util.Map;

import com.lind.basic.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class EntityUtilsTest {
  @Test
  public void mapToEntity() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", 1);
    map.put("name", "zzl");
    UserInfo user = EntityUtils.convertMapToObject(map, UserInfo.class);
    logger.info("map to entity {}", user);
  }



}
