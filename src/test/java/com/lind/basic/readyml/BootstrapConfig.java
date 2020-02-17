package com.lind.basic.readyml;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ConfigurationProperties
public class BootstrapConfig {
  @Value("${author}")
  private String author;

  @Value("${passport.authrates}")
  private List<String> authrates;

  @Value("${passport.users}")
  private List<String> users;

  @Autowired
  private Config config;

  @Test
  public void readConfigNode() {
    System.out.println(author);
    System.out.println(config.toString());
    users.forEach(System.out::println);
    authrates.forEach(System.out::println);
  }

}
