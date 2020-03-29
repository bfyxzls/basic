package com.lind.basic.beanfactory;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

@Slf4j
public class ApplicationContextTest {
  @Test
  public void classLoads() {
  }

  @Test
  public void loadBeanXml() {
    //加载项目中的spring配置文件到容器
    //ApplicationContext context = new ClassPathXmlApplicationContext("resouces/applicationContext.xml");
    //加载系统盘中的配置文件到容器
    ApplicationContext context = new FileSystemXmlApplicationContext("classpath:manBean.xml");
    //从容器中获取对象实例
    Man man = context.getBean(Man.class);
    logger.info(man.toString());
  }

  @Test
  public void loadBeanConfig() {
    //从java注解的配置中加载配置到容器
    ApplicationContext context = new AnnotationConfigApplicationContext(ManConfig.class);
    //从容器中获取对象实例
    Man man = context.getBean(Man.class);
    logger.info(man.toString());
  }

  @Test
  public void carBean() {
    ApplicationContext context = new ClassPathXmlApplicationContext("classpath:carBean.xml");
    Car car = context.getBean(Car.class);
    logger.info(car.toString());
  }
}
