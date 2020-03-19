package com.lind.basic.aspect;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 基于com.lind.basic.controller包下的方法进行拦截.
 */
@Aspect
@Component
public class ControllerAspect {
  private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

  /**
   * 横切点，哪些方法需要被横切.
   */
  @Pointcut(value = "execution(public * com.lind.basic.controller..*.*(..))")
  public void cutOffPoint() {
  }

  /**
   * @param joinPoint 具体的方法之前执行.
   */
  @Before("cutOffPoint()")
  public void doBefore(JoinPoint joinPoint) throws Throwable {
    logger.info("cutOffPoint.before...");
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = requestAttributes.getRequest();
    String requestURI = request.getRequestURI();
    String remoteAddr = request.getRemoteAddr();   //这个方法取客户端ip"不够好"
    String requestMethod = request.getMethod();
    String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
    String methodName = joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();
    logger.info("请求url=" + requestURI + ",客户端ip=" + remoteAddr + ",请求方式=" + requestMethod + ",请求的类名=" + declaringTypeName + ",方法名=" + methodName + ",入参=" + args);

  }

  /**
   * 解用于获取方法的返回值.
   *
   * @param obj 返回值
   */
  @AfterReturning(returning = "obj", pointcut = "cutOffPoint()")
  public void doBefore(Object obj) throws Throwable {
    logger.info("RESPONSE : " + obj);
  }


}
