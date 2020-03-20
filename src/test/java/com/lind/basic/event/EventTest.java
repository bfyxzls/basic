package com.lind.basic.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Profile("test")
public class EventTest {
  @Autowired
  private ApplicationContext context;

  @Test
  public void orderEvent() {
    context.publishEvent(new OrderEvent("ok"));
  }

}
