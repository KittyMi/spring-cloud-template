package com.andy.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.andy.core.bean.dto.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.andy.core.Constant.CURRENT_USER_KEY;


/**
 * @author ly
 * @date 2019/7/22 12:22
 * desc: Token解析器
 */
@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 处理token
        UserInfoDTO dto = JSON.parseObject(request.getHeader(CURRENT_USER_KEY), UserInfoDTO.class);
        if ( null != dto ) {
            request.setAttribute(CURRENT_USER_KEY, dto);
        }

        return true;
    }
}
