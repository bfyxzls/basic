[返回目录](../README.md)

### spring-ioc的使用
IOC容器在很多框架里都在使用，而在spring里它被应用的最大广泛，在框架层面
上，很多功能都使用了ioc技术，下面我们看一下ioc的使用方法。

1. 把服务注册到ioc容器
2. 使用属性注入反射对应类型的实例
3. 多态情况下，使用名称反射类型的实例

### 把服务注册到ioc容器
1. @Bean注册组件 

使用@Bean注解进行类型的注册，默认你的ioc容器里类型为bean的返回值，名称为bean所有的方法名，与
你的包名称没有直接关系，如果你的接口有多种实现，在注册时可以使用@Bean("lind")这种方式来声明。

2. @Component,@Configuration,Service,Repository注册组件 

这几个注解都是在类上面声明的，而@Bean是声明在方法上的，这一点要注意，这几个注解一般是指对一个
接口的实现，在实现类上加这些注解，例如，一个数据仓储接口UserRepository，它可以有多种数据持久
化的方式，如SqlUserRepositoryImpl和MongoUserRepositoryImpl，那么在注册时你需要为他们起
一个别名，如@Repository("Sql-UserRepositoryImpl) SqlUserRepositoryImpl，默认的名称是
类名，但注意`类名首字母为小写`。

```$xslt
public interface EmailLogService {
  void send(String email, String message);
}

@Component()
public class EmailLogServiceHttpImpl implements EmailLogService {
  private static final Logger logger = LoggerFactory.getLogger(EmailLogServiceHttpImpl.class);

  @Override
  public void send(String email, String message) {
    Assert.notNull(email, "email must not be null!");
    logger.info("send email:{},message:{}", email, message);
  }
}
@Component("email-socket")
public class EmailLogServiceSocketImpl implements EmailLogService {
  private static final Logger logger = LoggerFactory.getLogger(EmailLogServiceSocketImpl.class);

  @Override
  public void send(String email, String message) {
    Assert.notNull(email, "email must not be null!");
    logger.info("send email2:{},message:{}", email, message);
  }
}
// 看一下调用时的测试代码
  @Resource(name = "email-socket")
  EmailLogService socketEmail;
  @Autowired
  @Qualifier( "emailLogServiceHttpImpl")
  EmailLogService httpEmail;

  @Test
  public void testIoc2() {
    socketEmail.send("ok", "ok");
  }


  @Test
  public void testIoc1() {
    httpEmail.send("ok", "ok");
  }
```

### 在程序中使用bean对象
1. 使用Resource装配bean对象
在通过`别名`调用bean时，你可以使用@Resource注解来装配对象

2. 使用@Autowired装配bean对象
也可以使用 @Autowired 
@Qualifier( "emailLogServiceHttpImpl")两个注解去实现程序中的`多态`。

### 使用场景
在我们有些相同行为而实现方式不同的场景中，如版本1接口与版本2接口，在get方法实现有所不同，而这
两个版本都要同时保留，这时我们需要遵守`开闭原则`，扩展一个新的接口，而在业务上对代码进行重构，
提取两个版本相同的方法到基类，自己维护各自独有的方法，在为它们的bean起个名字，在装配时，通过
bean的名称进行装配即可。

写个伪代码：
```$xslt
class Api_version1(){
@Autowired 
@Qualifier("print-version1")
PrintService printService;
}

class Api_version2(){
@Autowired 
@Qualifier("print-version2")
PrintService printService;
}

class BasePrintService{}

interface PrintService{}

@Service("print-version1")
class PrintServiceImplVersion1 extends BasePrintService implements PrintService{}

@Service("print-version2")
class PrintServiceImplVersion2 extends BasePrintService implements PrintService{}

```
好了，这就是大叔总结的关于spring-ioc的一种东西！