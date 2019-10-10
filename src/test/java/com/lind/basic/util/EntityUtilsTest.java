package com.lind.basic.util;

import com.lind.basic.model.User;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class EntityUtilsTest {
  @Test
  public void mapToEntity() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", 1);
    map.put("username", "zzl");
    User user = EntityUtils.convertMapToObject(map, User.class);
    logger.info("map to entity {}", user);
  }



}
