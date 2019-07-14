[返回目录](../README.md)

### 微服务
主要将项目进行拆分，按着更细的粒度进行设计，各服务之间同步请求采用http restful方式进行通信，
异步请求采用消息中间件来实现，在java生态环境中，无论是restful请求还是消息中间件都有很多开源
的产品，供我们去选择。

### springcloud框架
springcloud是以springboot为基础进行开发的一套微服务框架工具集，它包括了很多实现微服务的理念，
如服务发现与注册，服务治理与监视，配置中心，加密与解密，请求分发与限流，网关服务，客户端负载均衡等。

1. 应用程序上下文服务
1. 配置中心:ConfigServer
1. 服务发现与注册:Eureka
1. 断路器:Hystrix
1. 路由器和过滤器:Zuul
1. 客户端负载均衡:Ribbon
1. 声明性REST客户端:Feign

#### 应用程序上下文服务
Bootstrap属性的优先级高，因此默认情况下不能被本地配置覆盖，使用`spring.cloud.bootstrap.enabled=false`
可以关闭引导功能。
bootstrap.yml
```
spring:
  application:
    name: foo
  cloud:
    config:
      uri: ${SPRING_CONFIG_URI:http://localhost:8888}
```
#### 配置中心
#### 服务发现与注册
#### 断路器
#### 路由器和过滤器
#### 客户端负载均衡
#### 声明性REST客户端Feign
Spring Cloud集成Ribbon和Eureka以在使用Feign时提供负载均衡的http客户端。
