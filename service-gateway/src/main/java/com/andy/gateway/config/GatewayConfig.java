package com.andy.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gateway配置
 * @author min.lai
 * @date 2020-07-22
 */
@Configuration
public class GatewayConfig {

    @Bean
    public AuthFilter authFilter(){
        return new AuthFilter();
    }
}
