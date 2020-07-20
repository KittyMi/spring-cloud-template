package com.andy.authorization.config;

import com.andy.authorization.exception.AuthGlobalExceptionResolver;
import com.andy.core.config.BaseWebMvcConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

/**
 * @author min.lai
 * @date 2019/8/26 14:54
 * desc: webMvcConfig
 */
@Configuration
public class WebMvcConfig implements BaseWebMvcConfig {

    /**
     * 参考WebMvcConfigurationSupport，配置configureHandlerExceptionResolvers会覆盖默认的异常处理器
     * 导致OAuth2大量的异常无法处理
     * @param resolvers List<HandlerExceptionResolver>
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        BaseWebMvcConfig.super.extendHandlerExceptionResolvers(resolvers);
        resolvers.add(new AuthGlobalExceptionResolver());
    }
}
