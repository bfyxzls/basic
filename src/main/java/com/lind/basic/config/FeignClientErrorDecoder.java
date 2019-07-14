package com.lind.basic.config;

import static feign.FeignException.errorStatus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lind.basic.exception.Exceptions;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * feign异常拦截器,当从feign抛出异常时走这个对象,注册当使用熔断后,会再走fallback返回值.
 * 在feignClient响应回来之前执行它，然后再进行响应，可以有异常时，再走fallback
 */
@Configuration
@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    logger.info("feign client response:", response);
    String body = null;
    try {
      body = Util.toString(response.body().asReader());
    } catch (IOException e) {
      logger.error("feign.IOException", e);
    }
    if (response.status() >= 400 && response.status() <= 500) {
      logger.info("FeignClientErrorDecoder.body {}", body);
      JSONObject object = (JSONObject) JSONObject.parse(body);
      String message = "客户端请求请求非法";
      if (object.containsKey("errors")) {
        JSONArray errors = object.getJSONArray("errors");
        if (errors.size() > 0) {
          JSONObject error = (JSONObject) errors.get(0);
          message = error.getString("message");
        }
        logger.info("FeignClientErrorDecoder.message1 {}", message);
        throw Exceptions.badRequestParams(message);
      } else if (object.containsKey("message")) {
        message = object.getString("message");
        logger.info("FeignClientErrorDecoder.message2 {}", message);
        throw Exceptions.badRequestParams(message);
      }
      logger.info("FeignClientErrorDecoder.message3 {}", message);
      throw Exceptions.badRequestParams(message);
    }
    return errorStatus(methodKey, response);
  }
}