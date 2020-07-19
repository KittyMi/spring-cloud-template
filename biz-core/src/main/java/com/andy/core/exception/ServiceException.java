package com.andy.core.exception;

import com.andy.core.bean.ErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ly
 * @date 2019/5/16 15:55
 * description: 微服务异常，用于微服务调用
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -8987879118709591943L;
    /**
     * ErrorCode Msg
     */
    @Getter
    private ErrorCode errorCode;
    @Getter
    @Setter
    private String errorMsg;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
        this.errorMsg = message;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     * @param errorCode ErrorCode
     */
    public ServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
