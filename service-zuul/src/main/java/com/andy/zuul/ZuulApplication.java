package com.andy.zuul;

import com.didispace.swagger.butler.EnableSwaggerButler;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author min.lai
 * Zuul网关
 */
@EnableFeignClients(
        basePackages = {
                "com.andy.core.feign",
        }
)
@ComponentScans(value = @ComponentScan(
        basePackages = {
                "com.andy.core.config"
        }
))
@EnableZuulProxy
@EnableEurekaClient
@SpringCloudApplication
@EnableSwaggerButler
public class ZuulApplication {

    public static void main(String[] args)  {
        SpringApplication.run(ZuulApplication.class,args);
    }
}
