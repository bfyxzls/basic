[返回目录](../README.md)

spring在启动时会自己把bean(java组件)注册到ioc容器里，实现控制反转，在开发人员使用spring开发应用程序时，你是看不到new关键字的，所有对象都应该从容器里获得，它们的`生命周期`在放入容器时已经确定！

下面说一下三种注册bean的方法
1. @ComponentScan
1. @Bean
1. @Import

### @ComponentScan注册指定包里的bean
Spring容器会扫描@ComponentScan配置的包路径，找到标记@Component注解的类加入到Spring容器。

我们经常用到的类似的（注册到IOC容器）注解还有如下几个：
- @Configuration：配置类 
- @Controller ：web控制器
- @Repository ：数据仓库
- @Service：业务逻辑

> 下面代码完成了EmailLogServiceImpl这个bean的注册，当然也可以放在@Bean里统一注册，需要看@Bean那一节里的介绍。
```
@Component
public class EmailLogServiceImpl implements EmailLogService {
  private static final Logger logger = LoggerFactory.getLogger(EmailLogServiceImpl.class);

  @Override
  public void send(String email, String message) {
    Assert.notNull(email, "email must not be null!");
    logger.info("send email:{},message:{}", email, message);
  }
}
```
### @Bean注解直接注册
注解@Bean被声明在方法上，方法都需要有一个返回类型，而这个类型就是注册到IOC容器的类型，接口和类都是可以的，介于面向接口原则，提倡返回类型为接口。

> 下面代码在一个@Configuration注解的类中，同时注册了多个bean。
```
@Configuration
public class LogServiceConfig {

  /**
   * 扩展printLogService行为，直接影响到LogService对象，因为LogService依赖于PrintLogService.
   *
   * @return
   */
  @Bean
  public PrintLogService printLogService() {
    return new PrintLogServiceImpl();
  }

  @Bean
  public EmailLogService emailLogService() {
    return new EmailLogServiceImpl();
  }

  @Bean
  public PrintLogService consolePrintLogService() {
    return new ConsolePrintLogService();
  }
}

```

### @Import注册Bean
这种方法最为直接，直接把指定的类型注册到IOC容器里，成为一个java bean，可以把@Import放在程序的八口，它在程序启动时自动完成注册bean的过程。
```
@Import({ LogService.class,PrintService.class })
public class RegistryBean {

}
```

Spring之所以如何受欢迎，我想很大原因是它自动化注册和自动化配置这一块的设计，确实让开发人员感到非常的自如，.net里也有类似的产品，像近几年比较流行的abp框架，大叔自己也写过类似的lind框架，都是基于自动化注册和自动化配置的理念。