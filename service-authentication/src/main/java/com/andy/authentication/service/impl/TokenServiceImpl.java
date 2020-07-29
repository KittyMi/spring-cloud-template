package com.andy.authentication.service.impl;

import com.andy.core.feign.IAuthorizationService;
import com.andy.authentication.service.ITokenService;
import com.andy.core.bean.dto.TokenDTO;
import com.andy.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static com.andy.core.bean.ErrorCode.UNAUTHORIZED;

/**
 * @author min.lai
 * @date 2019/7/23 14:32
 * desc: TokenService
 */
@Slf4j
@Service
public class TokenServiceImpl implements ITokenService {

    @Resource
    IAuthorizationService authorizationService;

    @Resource
    MapperFacade mapperFacade;

    @Resource
    RedisTokenStore tokenStore;

    @Override
    public Boolean removeToken(String token) {
        return authorizationService.removeToken(token);
    }

    /**
     * 检查token是否有效
     *
     * @param token token
     * @return
     */
    @Override
    public Boolean checkToken(String token) {

        Map<String, Object> resp = authorizationService.checkToken(token);
        return (Boolean) resp.get("active");
    }

    /**
     * token密码模式授权
     *
     * @param username 用户名
     * @param password 密码
     * @return OAuth2 TokenDTO
     */
    @Override
    public TokenDTO accessToken(String username, String password) {
        Map<String, String> map = new HashMap<>(6);
        map.put("username", username);
        map.put("password", password);
        map.put("grant_type", "password");
        map.put("scope", "read");
        map.put("client_id", "client");
        map.put("client_secret", "123456");
        // TODO 到时候去查权限表

        Principal principal = new UsernamePasswordAuthenticationToken("client", "123456");
        Map<String, Object> resp = authorizationService.accessToken(principal, map);
        return mapperFacade.map(resp, TokenDTO.class);
    }

    /**
     * 根据token读取AdditionalInformation中username
     *
     * @param token token
     * @return
     */
    @Override
    public Map<String, Object> readAdditionalInformation(String token) {

        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        if (null == accessToken || accessToken.isExpired()) {
            throw new ServiceException(UNAUTHORIZED);
        }

        return accessToken.getAdditionalInformation();
    }
}
