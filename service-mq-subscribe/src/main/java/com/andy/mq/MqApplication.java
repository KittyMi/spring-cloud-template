package com.andy.mq;

import com.andy.core.BaseApplication;
import org.springframework.boot.SpringApplication;

/**
 * @author min.lai
 * @date 2020-07-21
 * 服务启动入口
 */
public class MqApplication extends BaseApplication {
    public static void main( String[] args ) {
        SpringApplication.run(MqApplication.class,args);
    }

}
