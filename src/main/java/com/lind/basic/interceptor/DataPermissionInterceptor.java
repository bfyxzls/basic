package com.lind.basic.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 当前用户的数据权限.
 */
@Slf4j
public class DataPermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("DataPermissionInterceptor.init");
        //业务逻辑，可能判断当前登陆人存储的数据权限类型，然后统一处理，拿到可以访问的数据编号集合
        String[] ids = {"1", "2", "3"};
        request.setAttribute("approveIds", StringUtils.join(ids, ","));
        return true;
    }
}
