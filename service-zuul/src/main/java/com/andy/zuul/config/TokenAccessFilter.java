package com.andy.zuul.config;

import com.andy.core.bean.dto.UserInfoDTO;
import com.andy.core.feign.IAuthenticationService;
import com.andy.core.feign.IUserService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static com.andy.core.Constant.CURRENT_USER_KEY;
import static com.andy.core.bean.ErrorCode.UNAUTHORIZED;
import static com.andy.core.bean.ResultFactory.fail;

/**
 * @author min.lai
 * @date 2019/7/16 16:54
 * desc: 资源过滤器  用户解析
 */
@Slf4j
//@Component
public class TokenAccessFilter extends ZuulFilter {

    @Resource
    IUserService userService;

    @Resource
    IAuthenticationService authService;
    /**
     * 路由前拦截
     * @return pre
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序，越小优先级越高
     * @return 0
     */
    @Override
    public int filterOrder() {
        return 10001;
    }

    /**
     * 过滤器是否被执行，对所有请求生效
     * @return boolean
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤结果
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURI();

        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

        // 不需要网关授权的url
        if (authService.ignoreAuthentication(url)) {
            log.info("ignore url: {}", url);
            return null;
        }

        // 如果请求未携带token
        String token = extractToken(request);
        if (null == token) {
            unauthorized(ctx);
            return null;
        }

        // 携带token，先检查token，再检验权限
        if (authService.hasPermission(token, url, request.getMethod())) {
            // token和权限检验通过
            String username = authService.readUsername(token);
            UserInfoDTO dto = userService.getByUsername(username);

            if (null == dto) {
                log.error("用户不存在");
                unauthorized(ctx);
                return null;
            }
            ctx.addZuulRequestHeader(CURRENT_USER_KEY, dto.toString());
//            Boolean flag = userService.getVerifyCookie(request.getCookies(),token);
//            if (!flag){
//                unauthorized(ctx);
//            }
            return null;
        }
        unauthorized(ctx);
        return null;
    }


    /**
     * 授权失败
     * @param ctx
     */
    private void unauthorized(RequestContext ctx) {
        ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        ctx.setSendZuulResponse(false);
        ctx.setResponseBody(fail(UNAUTHORIZED).toString());
    }

    /**
     * 抽取
     * @param request 请求
     * @return String
     */
    final private static String BEARER_TYPE = "Bearer";
    final private static String ACCESS_TOKEN = "access_token";
    private String extractToken(HttpServletRequest request) {
        String ret = null;

        // Extract Header
        Enumeration<String> headers = request.getHeaders("Authorization");
        // typically there is only one (most servers enforce that)
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                ret = authHeaderValue;
                break;
            }
        }

        if (null == ret) {
            ret = request.getParameter(ACCESS_TOKEN);
        }
        return ret;
    }
}
