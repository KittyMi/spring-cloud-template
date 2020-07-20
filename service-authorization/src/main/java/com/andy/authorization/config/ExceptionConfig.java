package com.andy.authorization.config;

import com.andy.authorization.exception.CustomAccessDeniedHandler;
import com.andy.authorization.exception.CustomExceptionEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.HashMap;
import java.util.Map;

import static com.andy.core.bean.ErrorCode.PASSWORD_ERROR;
import static com.andy.core.bean.ErrorCode.TOKEN_INVALID;


/**
 * @author min.lai
 * @date 2019/7/31 16:25
 * desc: 异常处理，由过滤器处理ExceptionTranslationFilter
 * https://www.kancloud.cn/zhangchio/springboot/663136
 */
@Slf4j
@Configuration
public class ExceptionConfig {

    /**
     * AccessDeniedException访问异常
     * @return
     */
    @Bean
    AccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    /**
     * AuthenticationException认证异常
     * @return
     */
    @Bean
    AuthenticationEntryPoint customExceptionEntryPoint() {
        return new CustomExceptionEntryPoint();
    }

    /**
     * 自定义异常转换器
     *
     * @return WebResponseExceptionTranslator
     */
    @Bean
    WebResponseExceptionTranslator<OAuth2Exception> customWebResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
                OAuth2Exception oae = responseEntity.getBody();
//                HttpHeaders headers = new HttpHeaders();
//                headers.setAll(
//                        responseEntity.getHeaders().toSingleValueMap()
//                );


                Map<String, Object> map = new HashMap<>(3);
                map.put("flag", false);
                map.put("data", oae.toString());
                if (oae instanceof InvalidTokenException) {
                    // CheckTokenEndpoint 抛出
                    map.put("code", TOKEN_INVALID.getCode());
                } else if (oae instanceof InvalidGrantException) {
                    // TokenEndpoint 抛出
                    map.put("code", PASSWORD_ERROR.getCode());
                }
                log.error("e:{}, map:{}", e.toString(), map);

                // TODO 由于自定义异常使用fastjson无法序列化，此处强制转换
                return new ResponseEntity(
                        map,
                        HttpStatus.UNAUTHORIZED
                );

            }
        };
    }

}
