package com.andy.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lai.min
 * @date 2019/9/16 11:25
 * desc:
 */
@ConditionalOnMissingBean(
        name = "mybatisPlusConfig"
)
@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = "com.andy.*.mybatis.mapper")
@DependsOn( value = {"redisTemplate", "redisCacheTransfer"} )
public class DefaultMybatisPlusConfig {
    /**
     * @return 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
