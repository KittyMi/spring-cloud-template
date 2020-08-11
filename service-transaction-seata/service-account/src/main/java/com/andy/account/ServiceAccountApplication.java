package com.andy.account;

import com.andy.core.BaseApplication;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author min.lai
 * @date 2020-08-11
 */
@EnableAutoDataSourceProxy
public class ServiceAccountApplication extends BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAccountApplication.class, args);
    }

}
