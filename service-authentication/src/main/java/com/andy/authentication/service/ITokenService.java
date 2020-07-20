package com.andy.authentication.service;

import com.andy.core.bean.dto.TokenDTO;

import java.util.Map;

/**
 * @author min.lai
 * @date 2019/7/23 14:31
 * desc:
 */
public interface ITokenService {

    /**
     * 检查token是否有效
     * @param token token
     * @return
     */
    Boolean checkToken(String token);

    /**
     * token密码模式授权
     * @param username 用户名
     * @param password 密码
     * @return OAuth2 TokenDTO
     */
    TokenDTO accessToken(String username, String password);

    /**
     * 根据token读取AdditionalInformation中username
     * @param token token
     * @return
     */
    Map<String, Object> readAdditionalInformation(String token);
}
