package com.lind.basic.iocbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogService {

  private static final Logger logger = LoggerFactory.getLogger(LogService.class);
  // false表示可以没有这个bean
  @Autowired(required = false)
  PrintLogService printLogService;

  @Bean
  public LogService logServiceBean() {
    return new LogService();
  }

  void aop(String message) {
    if (printLogService != null) {
      printLogService.printMessage(message);
    }
  }

  public void logInfo(String message) {
    aop(message);
    logger.info("info:{}", message);
  }

  public void logDebug(String message) {
    aop(message);
    logger.info("debug:{}", message);
  }
}
