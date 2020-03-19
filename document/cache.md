[返回目录](../README.md)

### spring-cache的使用
spring框架里对cache支持的比较完善，对面向接口原则体现的比较明显，一个接口，多种缓存持久化的实现，
今天主要说一下，如何在springboot里实现缓存的功能。

#### 一个接口，多种实现
Cache接口为缓存的组件规范定义，目前spring框架为我们实现了很多缓存方式，如RedisCache，EhCacheCache,
ConcurrentMapCache等，今天主要用到了RedisCache。

#### 几种缓存注解的介绍
1. @Cacheable	主要针对方法配置，能够根据方法的请求参数对其进行缓存
1. @CacheEvict	清空缓存
1. @CachePut	保证方法被调用，又希望结果被缓存。与@Cacheable区别在于是否每次都调用方法，常用于更新
1. @EnableCaching	开启基于注解的缓存
1. @CacheConfig	统一配置本类的缓存注解的属性

#### 强大的缓存名称

##### name	
缓存的名称，在 spring 配置文件中定义，必须指定至少一个例如：`@Cacheable(name=”mycache”)` 
或者`@Cacheable(name={”cache1”,”cache2”}`等。

##### key
缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组
合,例如：`@Cacheable(name=”testcache”,key=”#id”)`

##### condition
缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存/清除缓存
例如：`@Cacheable(name=”testcache”,condition=”#userName.length()>2”)`

##### unless
否定缓存。当条件结果为TRUE时，就不会缓存。
`@Cacheable(name=”testcache”,unless=”#userName.length()>2”)`

##### allEntries
是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存,例如：`@CachEvict(name=”testcache”,allEntries=true)`

##### beforeInvocation
是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省
情况下，如果方法执行抛出异常，则不会清空缓存,例如：`@CachEvict(name=”testcache”，beforeInvocation=true)`