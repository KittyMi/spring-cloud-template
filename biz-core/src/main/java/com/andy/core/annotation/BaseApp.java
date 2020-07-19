package com.andy.core.annotation;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author ly
 * @date 2019/9/16 11:55
 * desc: 组合注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited

@EnableDiscoveryClient
@EnableSwagger2Doc
@SpringBootApplication(
        exclude = DruidDataSourceAutoConfigure.class,
        scanBasePackages = "com.andy.*.config"
)
@EnableFeignClients(
        basePackages = "com.andy.*.feign"
)
public @interface BaseApp {

    @AliasFor(annotation = SpringBootApplication.class, attribute = "exclude")
    Class<?>[] exclude() default {};

    @AliasFor(annotation = EnableFeignClients.class, attribute = "basePackages")
    String[] feignBasePackages() default {};


}
