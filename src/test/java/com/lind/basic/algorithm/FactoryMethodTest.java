package com.lind.basic.algorithm;

import org.junit.Test;

public class FactoryMethodTest {
  @Test
  public void factoryTest() {
    LoggerFactory databaseLoggerFactory = new DatabaseLoggerFactory();
    Logger logger = databaseLoggerFactory.createLogger();
    logger.writeLog();
  }

  public interface Logger {
    public void writeLog();
  }

  public interface LoggerFactory {
    public Logger createLogger();

  }

  public class DatabaseLogger implements Logger {

    @Override
    public void writeLog() {
      // TODO Auto-generated method stub
      System.out.println("数据库日志记录");
    }
  }

  public class DatabaseLoggerFactory implements LoggerFactory {

    @Override
    public Logger createLogger() {
      // TODO Auto-generated method stub
      Logger logger = new DatabaseLogger();

      return logger;
    }
  }

}
