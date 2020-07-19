package com.andy.core.config;

import com.andy.core.exception.GlobalExceptionResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

/**
 * @author min.lai
 * @date 2019/8/26 14:49
 * desc:
 */
@Configuration
@ConditionalOnMissingBean(name = "webMvcConfig")
public class DefaultWebMvcConfig implements BaseWebMvcConfig{

    /**
     * 配置全局异常处理器
     * @param resolvers
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        BaseWebMvcConfig.super.configureHandlerExceptionResolvers(resolvers);
        resolvers.add(new GlobalExceptionResolver());
    }

}
