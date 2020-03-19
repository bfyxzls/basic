package com.lind.basic.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lind.basic.BaseTest;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

public class ResourceTest extends BaseTest {

  @Test
  public void readFile() throws Exception {
    List<Map<String, Object>> result = fromJsonArray("requestUserDto.json",
        new TypeReference<List<Map<String, Object>>>() {
        });
    String name = result.get(0).get("username").toString();
    Assert.assertEquals("zzl", name);
  }
}
