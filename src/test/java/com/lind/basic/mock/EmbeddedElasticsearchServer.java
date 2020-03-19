package com.lind.basic.mock;


import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.allegro.tech.embeddedelasticsearch.EmbeddedElastic;
import pl.allegro.tech.embeddedelasticsearch.PopularProperties;

@Component
public class EmbeddedElasticsearchServer {
  @Autowired
  EmbeddedElastic embeddedElastic;

  @Bean
  public EmbeddedElastic embeddedElastic() {
    EmbeddedElastic embeddedElastic = EmbeddedElastic.builder()
        .withElasticVersion("5.5.2")
        .withSetting(PopularProperties.HTTP_PORT, 21121)
        .build();
    return embeddedElastic;
  }

  @PostConstruct
  public void startRedis() throws IOException, InterruptedException {
    embeddedElastic.start();

  }

  @PreDestroy
  public void stopRedis() {
    embeddedElastic.stop();
  }
}
