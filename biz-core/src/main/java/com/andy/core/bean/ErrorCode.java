package com.andy.core.bean;


import lombok.Getter;
import lombok.Setter;

/**
 * @author ly
 * @date 2019/5/6 17:26
 * description: 响应码和msg
 */
public enum ErrorCode {
    /**
     * 公用模块(00) 100001->版本1，00表示公用模块，001错误码编号
     */
    // 文件上传失败
    FILE_UPLOAD_FAILED(100001),
    // 文件md5校验失败
    FILE_MD5_FAILED(100002),


    // 用户名格式错误
    USERNAME_PATTERN_ERROR(101001),
    // 密码格式错误
    PASSWORD_PATTERN_ERROR(101002),

    // 用户名不存在
    USERNAME_NOT_FOUND(101003),
    // 密码错误
    PASSWORD_ERROR(101004),
    // 邮箱格式错误
    EMAIL_PATTERN_ERROR(101005),

    /**
     * 权限相关
     */
    // token过期
    TOKEN_EXPIRED(102001),
    // token无效
    TOKEN_INVALID(102002),


    /**
     * 通用模块
     */
    // 未授权
    UNAUTHORIZED(401),

    // 失败
    FAIL(-1),
    // Excel没有数据
    EXCEL_NOT_DATA(700000),
    // Excel没有数据
    EXCEL_DATA_ERROR(700000),

    // 成功
    SUCCESS(0);


    @Setter
    @Getter
    private int code;

    ErrorCode(int code) {
        this.code = code;
    }
}
