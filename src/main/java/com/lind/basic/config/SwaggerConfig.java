package com.lind.basic.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2的配置.
 * 地址：http://localhost:8080/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket createRestApi() {
    Predicate<RequestHandler> predicate = input -> {
      Class<?> declaringClass = input.declaringClass();
      if (declaringClass == BasicErrorController.class)// 排除
      {
        return false;
      }
      return true;
    };
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .useDefaultResponseMessages(false)
        .select()
        .apis(predicate)
        .paths(Predicates.not(PathSelectors.regex("/actuator.*"))) //排队actuator控制器
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("仓储大叔api说明文档")//大标题
        .version("1.0")//版本
        .build();
  }
}