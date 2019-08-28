package com.lind.basic.grammar;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

interface PeopleApplicationListener {
  default ApplicationListener<PeopleRun> peopleRun() {
    return new PeopleRunListener();
  }

  default ApplicationListener<ManRun> manRun() {
    return new ManRunListener();
  }

  class PeopleRunListener implements ApplicationListener<PeopleRun> {

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(PeopleRun event) {

    }
  }

  class ManRunListener implements ApplicationListener<ManRun> {

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ManRun event) {

    }
  }
}

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ClassInheritInterface {
  @Autowired
  ApplicationContext applicationContext;

  @Test
  public void test() {
    applicationContext.publishEvent(new PeopleRun("dd", "dd"));
    applicationContext.publishEvent(new ManRun("dd", "dd"));

  }

}

class PeopleRun extends ApplicationEvent {
  private String name;

  public PeopleRun(Object source, String name) {
    super(source);
    this.name = name;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

class ManRun extends ApplicationEvent {
  private String name;

  public ManRun(Object source, String name) {
    super(source);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

@Component
class PeopleListener implements PeopleApplicationListener {


}