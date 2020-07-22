package com.andy.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author min.lai
 * Zuul网关
 */
@EnableFeignClients(
        basePackages = {
                "com.andy.*.feign",
        }
)
@ComponentScans(value = @ComponentScan(
        basePackages = {
                "com.andy.core.config"
        }
))
@SpringCloudApplication
public class GatewayApplication {

    public static void main(String[] args){
        SpringApplication.run(GatewayApplication.class,args);
    }



}
