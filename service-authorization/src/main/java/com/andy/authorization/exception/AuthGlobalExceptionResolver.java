package com.andy.authorization.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.andy.core.exception.GlobalExceptionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.andy.core.bean.ErrorCode.PASSWORD_ERROR;

/**
 * @author min.lai
 * @date 2019/7/29 15:37
 * desc:
 */
@Slf4j
public class AuthGlobalExceptionResolver extends GlobalExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error("{}, {}", e.getClass().getName(), e.getMessage());

        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> map = new HashMap<>(3);
        map.put("flag", false);

        // ResourceOwnerPasswordTokenGranter
        if ( e instanceof InvalidGrantException) {

            // 账号或密码错误
            map.put("data", e.getMessage());
            map.put("code", PASSWORD_ERROR.getCode());

            ModelAndView mv = new ModelAndView();
            FastJsonJsonView view = new FastJsonJsonView();
            view.setAttributesMap(map);
            mv.setView(view);

            return mv;
        }


        return super.resolveException(httpServletRequest, httpServletResponse, o, e);
    }
}
