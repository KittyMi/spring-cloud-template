package com.andy.core.bean;

import lombok.NonNull;

import static com.andy.core.Constant.EMPTY_OBJECT;
import static com.andy.core.bean.ErrorCode.FAIL;
import static com.baomidou.mybatisplus.extension.enums.ApiErrorCode.SUCCESS;

/**
 * @author ly
 * @date 2019/5/30 15:26
 * desc: ResultEntity的工厂方法
 */
public class ResultFactory {

    /**
     * 成功（无返回值）
     * @return  ResultEntity<T>
     */
    @NonNull
    public static ResultEntity success() {
        return success(EMPTY_OBJECT);
    }

    /**
     * 成功（带返回值）
     * @param data 数据
     * @param <T>  类型
     * @return  ResultEntity<T>
     */
    @NonNull
    public static <T> ResultEntity<T> success(@NonNull T data) {
        return new ResultEntity<T>()
                .setFlag(true)
                .setCode((int) SUCCESS.getCode())
                .setData(data);
    }


    /**
     * 失败（带错误原因）
     * @param errorCode ErrorCode
     * @return ResultEntity
     */
    @NonNull
    public static ResultEntity fail(@NonNull ErrorCode errorCode) {
        return new ResultEntity<>()
                .setFlag(false)
                .setCode(errorCode.getCode())
                .setData(EMPTY_OBJECT);
    }
    /**
     * 失败（带错误原因）
     */
    @NonNull
    public static ResultEntity fail(ErrorCode errorCode, String msg) {
        return new ResultEntity<>()
                .setFlag(false)
                .setCode(errorCode.getCode())
                .setData(msg);
    }

    /**
     * 失败（默认错误原因）
     * @return ResultEntity
     */
    @NonNull
    public static ResultEntity fail() {
        return fail(FAIL);
    }
}

