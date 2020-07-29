package com.andy.authentication.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

/**
 * @author min.lai
 * @date 2019/7/25 15:48
 * desc: 授权服务器属性
 */
@ConfigurationProperties("andy.oauth2")
@Setter
@Getter
class AuthServerProperties {

    private Integer accessTokenValiditySeconds = 60 * 60 * 24 * 7;

    private Integer refreshTokenValiditySeconds = 60 * 60 * 24 * 7;

    private String clientId = "client";

    private String clientSecret = "123456";

    private List<String> scopes = Arrays.asList("read");

    private List<String> authorizedGrantTypes = Arrays.asList(
            "password", "refresh_token"
    );

    private Boolean supportRefreshToken = true;
}
