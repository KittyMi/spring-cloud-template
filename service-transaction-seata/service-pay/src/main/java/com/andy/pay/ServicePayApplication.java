package com.andy.pay;

import com.andy.core.BaseApplication;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;

/**
 * @author min.lai
 * @date 2020-08-10
 */
@EnableAutoDataSourceProxy
public class ServicePayApplication extends BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicePayApplication.class, args);
    }

}
