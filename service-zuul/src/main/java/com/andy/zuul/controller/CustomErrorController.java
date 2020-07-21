package com.andy.zuul.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.andy.core.bean.Result;
import com.andy.core.bean.ResultFactory;
import com.andy.core.exception.RpcServiceException;
import com.netflix.zuul.exception.ZuulException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author min.lai
 * @date 2019/8/1 17:38
 * desc: 自定义zuul错误处理器
 */
@Slf4j
@RestController
public class CustomErrorController {

    @GetMapping(value = "/error")
    public ResponseEntity<Result> error(HttpServletRequest request) {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (throwable instanceof ZuulException) {
            Throwable cause = throwable.getCause();

            if (cause instanceof RpcServiceException) {
                Throwable cause1 = cause.getCause();
                if (cause1 instanceof FeignException) {
                    FeignException e = (FeignException) cause1;
                    return new ResponseEntity<>(
                            JSON.parseObject(cause.getMessage(), new TypeReference<Result<String>>(){}),
                            HttpStatus.valueOf(e.status())
                    );
                } else {
                    return new ResponseEntity<>(
                            JSON.parseObject(cause.getMessage(), new TypeReference<Result<String>>(){}),
                            HttpStatus.BAD_GATEWAY
                    );
                }
            }
        }

        return new ResponseEntity<>(ResultFactory.fail(), HttpStatus.BAD_GATEWAY);
    }
}
