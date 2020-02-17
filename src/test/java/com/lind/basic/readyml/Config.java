package com.lind.basic.readyml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "config-attributes")
@ToString
public class Config {
  private String value;
  private String[] valueArray;
  private List<String> valueList;
  private HashMap<String, String> valueMap;
  private List<Map<String, String>> valueMapList;
}
