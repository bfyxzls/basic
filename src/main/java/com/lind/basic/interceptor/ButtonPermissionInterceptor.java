package com.lind.basic.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 当前用户的安全权限.
 */
@Slf4j
public class ButtonPermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("ButtonPermissionInterceptor.init");
        String[] ids = {"1", "2", "3"};
        request.setAttribute("buttonIds", StringUtils.join(ids, ","));
        return true;
    }
}
