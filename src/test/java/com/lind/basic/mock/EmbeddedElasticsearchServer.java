//package com.lind.basic.mock;
//
//
//import java.io.IOException;
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import pl.allegro.tech.embeddedelasticsearch.EmbeddedElastic;
//import pl.allegro.tech.embeddedelasticsearch.PopularProperties;
//
//@Component //先注释，这个zip包总是下载失败
//public class EmbeddedElasticsearchServer {
//  @Autowired
//  EmbeddedElastic embeddedElastic;
//
//  @Bean
//  public EmbeddedElastic embeddedElastic() {
//    EmbeddedElastic embeddedElastic = EmbeddedElastic.builder()
//        .withElasticVersion("5.0.0")
//        .withSetting(PopularProperties.HTTP_PORT, 21121)
//        .build();
//    return embeddedElastic;
//  }
//
//  @PostConstruct
//  public void startRedis() throws IOException, InterruptedException {
//    embeddedElastic.start();
//
//  }
//
//  @PreDestroy
//  public void stopRedis() {
//    embeddedElastic.stop();
//  }
//}
