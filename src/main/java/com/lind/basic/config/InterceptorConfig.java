package com.lind.basic.config;

import com.lind.basic.interceptor.ButtonPermissionInterceptor;
import com.lind.basic.interceptor.DataPermissionInterceptor;
import com.lind.basic.interceptor.MybatisInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new DataPermissionInterceptor()).addPathPatterns("/approve/**");
        registry.addInterceptor(new ButtonPermissionInterceptor()).addPathPatterns("/approve/**");
    }


}
