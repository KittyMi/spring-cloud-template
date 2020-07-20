package com.andy.authentication;

import com.andy.core.BaseApplication;
import org.springframework.boot.SpringApplication;

/**
 * 鉴权服务
 * @author min.lai
 * @date 2020-07-20
 */
public class AuthenticationApplication extends BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class,args);
    }
}
