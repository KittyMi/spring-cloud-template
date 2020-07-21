package com.andy.user;

import com.andy.core.BaseApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @author min.lai
 * @date 2020-07-21
 * 用户服务启动入口
 */
@Slf4j
public class UserApplication extends BaseApplication {
    public static void main( String[] args ) {
        SpringApplication.run(UserApplication.class,args);
        log.info("用户服务模块启动成功");
    }
}
