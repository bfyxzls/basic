package com.lind.basic.iocbean;

public class ConsolePrintLogService implements PrintLogService {
  @Override
  public void printMessage(String message) {
    System.out.println("ConsolePrintLogService.printMessage:" + message);
  }
}
