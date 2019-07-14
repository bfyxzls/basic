[返回目录](../README.md)

### @ActiveProfiles的介绍
官方解释：@ActiveProfiles可以用来在测试的时候启用某些Profile的Bean。
大叔解释：如果你指定的是测试环境变量，那么在启动时会优先加载@ActiveProfiles的bean，然后才
会加载其它的bean，比如当其它bean用到了你的某个在测试环境用的bean时，你可以把这个bean声明为
@ActiveProfiles即可。

### redis-mock
> 在测试环境模拟redis时，一般来说就是模拟一个RedisConnectionFactory，这时为了保证
你的redisMock被优先加载，我们会添加@ActiveProfiles("test")注解，这时它会优化其它
的bean被加载。

```$xslt
@ActiveProfiles("test")
@Configuration
public class RedisServerMock {

  private RedisServer redisServer;
  private String redisHost = "localhost";
  private int redisPort = RandomUtils.getRandomInt(58000, 60000);

  @Bean
  RedisConnectionFactory redisConnectionFactory() {

    RedisStandaloneConfiguration redisStandaloneConfiguration =
        new RedisStandaloneConfiguration(redisHost, redisPort);
    RedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration);
    return factory;
  }

  @PostConstruct
  public void startRedis() throws IOException {
    redisServer = new RedisServer(redisPort);
    redisServer.start();
  }

  @PreDestroy
  public void stopRedis() {
    redisServer.stop();
  }
}
```