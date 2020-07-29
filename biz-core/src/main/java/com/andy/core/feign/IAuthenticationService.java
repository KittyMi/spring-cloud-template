package com.andy.core.feign;

import com.andy.core.bean.dto.TokenDTO;
import com.andy.core.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "service-authentication",
        configuration = {
                FeignConfig.class
        }
)
public interface IAuthenticationService {


    @GetMapping(value = "/authentication/remove_token")
    Boolean removeToken(@RequestParam("token") String token);

    /**
     * 检查token是否有效
     * @param token token
     * @return boolean
     */
    @GetMapping(value = "/authentication/check_token")
    Boolean checkToken(@RequestParam("token") String token);

    /**
     * token密码模式授权
     * @param username 用户名
     * @param password 密码
     * @return OAuth2 TokenDTO
     */
    @GetMapping(value = "/authentication/token")
    TokenDTO accessToken(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );

    /**
     * 根据token读取AdditionalInformation中username
     * @param token token
     * @return Username
     */
    @GetMapping(value = "/authentication/read_username")
    String readUsername(
            @RequestParam("token") String token
    );

    /**
     * 检查是否无需鉴权
     * @param url url
     * @return
     */
    @GetMapping(value = "/authentication/ignore_authentication")
    Boolean ignoreAuthentication(
            @RequestParam("url") String url
    );

    /**
     * 是否有权限
     * @param token token
     * @param url url
     * @param method method
     * @return
     */
    @GetMapping(value = "/authentication/has_permission")
    Boolean hasPermission(
            @RequestParam("token") String token,
            @RequestParam("url") String url,
            @RequestParam("method") String method
    );
}
