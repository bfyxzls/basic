package com.lind.basic.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CorsConfig implements Filter {

  public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
  public static final String ORIGIN = "origin";
  public static final String ANY_ORIGINS = "*";

  @Value("${cors.allowed.origins:*}")
  private String[] allowedOrigins;

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void doFilter(ServletRequest servletRequest,
                       ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    HttpServletRequest request = (HttpServletRequest) servletRequest;

    String originHeader = request.getHeader(ORIGIN);

    if (originHeader != null) {
      //如果是跨域访问，在Response的头部中加上Access-Control-Allow-Origin信息
      String rqHeader = URLEncoder.encode(originHeader, StandardCharsets.UTF_8.displayName());
      if (Arrays.asList((allowedOrigins)).contains(ANY_ORIGINS)) {
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, rqHeader);
      } else {
        for (String domain : allowedOrigins) {

          if (originHeader.endsWith(domain)) {
            response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, rqHeader);
            break;
          }
        }
      }
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
  }
}