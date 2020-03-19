package com.lind.basic.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteLog {
  @Test
  public void infoLog() {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    logger.info("ok");

  }
}
