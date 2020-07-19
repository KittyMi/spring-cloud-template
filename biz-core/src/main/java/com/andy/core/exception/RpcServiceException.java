package com.andy.core.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;

/**
 * @author ly
 * @date 2019/7/25 20:54
 * desc: 微服务RPC异常处理
 */
public class RpcServiceException extends HystrixBadRequestException {

    private static final long serialVersionUID = -8074523020268880197L;

    public RpcServiceException(String message) {
        super(message);
    }

    public RpcServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
