package com.andy.core.config;

import com.andy.core.interceptor.CurrentUserMethodArgumentResolver;
import com.andy.core.interceptor.LogInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author ly
 * @date 2019/8/26 14:45
 * desc: 默认的WebMvcConfigurer
 */
public interface BaseWebMvcConfig extends WebMvcConfigurer {

    /**
     * 日志
     * @param registry
     */
    @Override
    default void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor());
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    /**
     * Current user
     * @param resolvers
     */
    @Override
    default void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserMethodArgumentResolver());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
