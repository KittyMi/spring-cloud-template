package com.andy.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.andy.core.Constant;
import com.andy.core.annotation.CurrentUser;
import com.andy.core.bean.dto.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * @author ly
 * @date 2018/3/22 11:55
 */
@Slf4j
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.hasParameterAnnotation(CurrentUser.class) &&
                methodParameter.getParameterType().isAssignableFrom(UserInfoDTO.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory
    ) throws Exception {
        UserInfoDTO dto = JSON.parseObject(nativeWebRequest.getHeader(Constant.CURRENT_USER_KEY), UserInfoDTO.class);
        if (null != dto) {
            return dto;
        }

        throw new MissingServletRequestPartException(Constant.CURRENT_USER_KEY);
    }
}
