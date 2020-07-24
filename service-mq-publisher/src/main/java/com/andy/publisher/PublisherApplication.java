package com.andy.publisher;

import com.andy.core.BaseApplication;
import org.springframework.boot.SpringApplication;

/**
 * @author min.lai
 * @date 2020-07-21
 * 服务启动入口
 */
public class PublisherApplication extends BaseApplication {
    public static void main( String[] args ) {
        SpringApplication.run(PublisherApplication.class,args);
    }

}
