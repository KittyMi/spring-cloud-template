package com.andy.authorization.config;

import com.andy.authorization.exception.CustomAccessDeniedHandler;
import com.andy.authorization.exception.CustomExceptionEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author min.lai
 * @date 2019/6/25 17:53
 * desc: OAuth2 授权服务器
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(AuthServerProperties.class)
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    AuthServerProperties properties;

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    RedisConnectionFactory connectionFactory;

    @Resource
    UserDetailsService userDetailsService;

    /**
     * 异常处理
     */
    @Resource
    WebResponseExceptionTranslator<OAuth2Exception> customWebResponseExceptionTranslator;

    @Resource
    CustomExceptionEntryPoint customExceptionEntryPoint;

    @Resource
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(properties.getClientId())
                .secret(passwordEncoder.encode(properties.getClientSecret()))
                .scopes(properties.getScopes().toArray(new String[0]))
                .authorizedGrantTypes(properties.getAuthorizedGrantTypes().toArray(new String[0]));
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .checkTokenAccess("permitAll()")
                // allowFormAuthenticationForClients为注册ClientCredentialsTokenEndpointFilter
                // 解析request中的client_id和client_secret构造成UsernamePasswordAuthenticationToken
                // 通过UserDetailsServices查询认证，一般是针对password和client_credentials模式，让/oauth/token
                // 支持client_id和client_secret用作登录认证
                .allowFormAuthenticationForClients()
                .authenticationEntryPoint(customExceptionEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenServices(defaultTokenServices())
                .exceptionTranslator(customWebResponseExceptionTranslator)

                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                //修改默认的哦啊oauth2的默认跳转
                .pathMapping("/oauth/check_token", "/auth/check_token")
                .pathMapping("/oauth/token", "/auth/token")
        ;
    }

    /**
     * 默认TokenServices
     * @return DefaultTokenServices
     */
    @Bean
    DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setSupportRefreshToken(properties.getSupportRefreshToken());
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setAccessTokenValiditySeconds(properties.getAccessTokenValiditySeconds());
        defaultTokenServices.setRefreshTokenValiditySeconds(properties.getRefreshTokenValiditySeconds());
        defaultTokenServices.setTokenEnhancer(tokenEnhancer());

        return defaultTokenServices;
    }

    /**
     * 采用RedisTokenStore存储token
     * @return TokenStore
     */
    @Bean
    RedisTokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }

    /**
     * 添加username
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer(){
        return (accessToken, authentication) -> {
            if (accessToken instanceof DefaultOAuth2AccessToken){
                DefaultOAuth2AccessToken token= (DefaultOAuth2AccessToken) accessToken;
                Map<String, Object> additionalInformation = new LinkedHashMap<>();
                additionalInformation.put("username",authentication.getName());
                token.setAdditionalInformation(additionalInformation);
            }
            return accessToken;
        };
    }
}
