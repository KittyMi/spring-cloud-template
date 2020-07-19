package com.andy.core.exception;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * @author min.lai
 * @date 2019/7/25 20:44
 * desc: Feign异常解析器
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
       Exception exception = null;
       String body = null;

        try {
            body = Util.toString(response.body().asReader());
            log.error(body);

            exception = FeignException.errorStatus(s, response);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        // 封装400-500开头的请求异常
        if (HttpStatus.BAD_REQUEST.value() <= response.status() && response.status() < HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            // 不进入熔断，直接抛出异常
            exception = new RpcServiceException(body, exception);
        } else {
            log.error(exception.getMessage(), exception);
        }
        return exception;
    }
}
