package com.lind.basic.iocbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintLogServiceImpl implements PrintLogService {
  private static final Logger logger = LoggerFactory.getLogger(LogService.class);

  @Override
  public void printMessage(String message) {
    logger.info("printMessage:{}", message);
  }
}
