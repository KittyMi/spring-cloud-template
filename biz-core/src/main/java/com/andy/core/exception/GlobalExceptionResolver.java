package com.andy.core.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.andy.core.bean.ErrorCode;
import com.andy.core.bean.R;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.andy.core.Constant.EMPTY_OBJECT;

/**
 * @author min.lai
 * @date 2019/5/29 18:02
 * desc: 全局异常处理器
 */
@Slf4j
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        Map<String, Object> attributes = new HashMap<>(3);
        attributes.put("data", EMPTY_OBJECT);
        attributes.put("flag", false);

        if (e instanceof ServiceException) {
            // 微服务异常
            ErrorCode errorCode = ((ServiceException)e).getErrorCode();
            if (null != errorCode) {
                attributes.put("code", errorCode.getCode());
            } else {
                attributes.put("code", ErrorCode.FAIL.getCode());

                Throwable cause = e.getCause();
                if (null != cause) {
                    attributes.put("data", cause.getLocalizedMessage());
                } else {
                    attributes.put("data", e.getLocalizedMessage());
                }
            }
        } else if (e instanceof BindException || e instanceof MethodArgumentNotValidException) {
            // Spring Valid参数绑定验证异常
            BindingResult result = (e instanceof BindException)
                    ? ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();
            List<FieldError> lst = result.getFieldErrors();
            if (!lst.isEmpty()) {
                FieldError fe = lst.get(0);
                attributes.put("code", fe.getDefaultMessage());
                attributes.put("data", e.getLocalizedMessage());
            }
        } else if (e instanceof RpcServiceException) {
            // RpcServiceException: message, cause

            Throwable cause = e.getCause();

            if (cause instanceof FeignException) {
                // 由FeignErrorDecoder处理
                int status = ((FeignException) cause).status();
                httpServletResponse.setStatus(status);

            } else {
                // 非FeignException设置response返回值为400
                httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            }

            // RpcServiceException保证Message比不为空，默认为ResultEntity<String>的Json字符串
            String msg = e.getMessage();
            R<String> r = JSON.parseObject(
                    msg,
                    new TypeReference<R<String>>() {}
            );

            attributes.put("data", r.getData());
            attributes.put("code", r.getCode());
            attributes.put("flag", r.getFlag());

        } else {
            if (o instanceof HandlerMethod) {
                HandlerMethod method = (HandlerMethod) o;
                StringJoiner sj = new StringJoiner("|");

                sj.add("接口：[" + httpServletRequest.getRequestURI() + "]出现异常");
                sj.add("方法：" + method.getBean().getClass().getName() + "." + method.getMethod().getName());
                sj.add("异常信息：" + e.getLocalizedMessage());

                attributes.put("data", sj.toString());
                attributes.put("code", ErrorCode.FAIL.getCode());

                // TODO ?
                log.error(ExceptionUtils.getFullStackTrace(e));
            }
        }

        log.error("{}, {}, {}", httpServletResponse.getStatus(), attributes, e.getLocalizedMessage());

        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }
}
