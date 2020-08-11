package com.andy.gateway.config;

import com.alibaba.fastjson.JSONObject;
import com.andy.gateway.dto.UserInfoDTO;
import com.andy.gateway.feign.IAuthService;
import com.andy.gateway.feign.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 认证过滤器 校验token
 * @author min.lai
 * @date 2020/5/25 15:30
 */
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    private static final String CURRENT_USER_KEY = "current_user";

    @Resource
    IAuthService authService;

    @Resource
    IUserService userService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String uri = request.getURI().getPath();

        log.info(
                "method:{}, uri:{}, url:{}",
                request.getMethod(),
                request.getURI().getPath(),
                request.getURI()
        );


        // 白名单
        if (authService.ignoreAuthentication(uri)) {
            log.info("white list uri: {}", uri);
            return chain.filter(exchange);
        }

        // 非白名单并且请求未携带token
        String token = extractToken(exchange.getRequest());
        if (Objects.isNull(token)) {
            return unauthorized(response);
        }

        log.info("请求token:{}", token);
        log.info("鉴权完毕");

        // 检查token
        if (authService.hasPermission(token, uri, request.getMethodValue())) {
            // token和权限检验通过
            String username = authService.readUsername(token);
            log.info("username:{}, dto:{}", username, userService.getByUsername(username));
            UserInfoDTO dto = userService.getByUsername(username);

            if (Objects.isNull(dto)) {
                log.error("用户{}不存在", username);
                return unauthorized(response);
            }
            // 携带用户信息
            ServerHttpRequest mutableRequest = request.mutate().header(CURRENT_USER_KEY, dto.toString()).build();
            return chain.filter(exchange.mutate().request(mutableRequest).build());
        }
        return unauthorized(response);
    }

    @Override
    public int getOrder() {
        return -20;
    }

    /**
     * 授权失败
     * @param response
     * @return
     */
    private Mono<Void> unauthorized(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 401);
        jsonObject.put("flag", false);
        jsonObject.put("data", "");
        return response.writeWith(
                Mono.just(
                        response.bufferFactory().wrap(
                                jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8)
                        )
                )
        );
    }

    private final static String BEARER_TYPE = "Bearer";
    private final static String ACCESS_TOKEN = "access_token";
    private final static String AUTH_KEY = "Authorization";

    private String extractToken(ServerHttpRequest request) {
        String ret = null;
        // Extract Header
        String value = request.getHeaders().getFirst(AUTH_KEY);
        if (!StringUtils.isEmpty(value)) {
            if (value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
                ret = value.substring(BEARER_TYPE.length()).trim();
            }
        }

        if (Objects.isNull(ret)) {
            // Extract Params
            ret = request.getQueryParams().getFirst(ACCESS_TOKEN);
        }

        return ret;
    }
}
