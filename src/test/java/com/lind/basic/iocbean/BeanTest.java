package com.lind.basic.iocbean;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

@Profile("test")
@RunWith(SpringRunner.class)
@SpringBootTest()
public class BeanTest {
  @Resource(name = "printLogService")
  PrintLogService printLogService;

  @Test
  public void testIoc2() {
    printLogService.printMessage("ok");
  }

}
