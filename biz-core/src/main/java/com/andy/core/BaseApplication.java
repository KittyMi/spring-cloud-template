package com.andy.core;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.andy.core.annotation.BaseApp;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author min.lai
 * @date 2020-07-19
 * description: SpringCloud应用基类
 * 1. ComponentScans用于手动扫描多模块下的Spring Bean Config
 * 2. 排除DruidDataSourceAutoConfigure兼容Mybatis-Plus Druid连接
 * 3. 开启Eureka微服务注册发现
 * 4. 开启Swagger
 */
@SpringBootApplication(
        exclude = DruidDataSourceAutoConfigure.class
)
@ComponentScan(
        basePackages = {
                "com.andy.*.config"
        }
)
@EnableFeignClients( basePackages = "com.andy.*.feign" )
@EnableDiscoveryClient
@EnableSwagger2Doc
//@BaseApp
public class BaseApplication {

}
