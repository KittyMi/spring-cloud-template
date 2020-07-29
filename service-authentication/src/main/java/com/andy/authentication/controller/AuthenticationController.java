package com.andy.authentication.controller;

import com.andy.authentication.service.IPermissionService;
import com.andy.authentication.service.ITokenService;
import com.andy.core.bean.dto.TokenDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author min.lai
 * @date 2019/7/23 11:23
 * desc: 鉴权服务器
 */
@Slf4j
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Resource
    ITokenService tokenService;

    @Resource
    IPermissionService permissionService;


    @GetMapping(value = "/remove_token")
    Boolean removeToken(@RequestParam("token") String token) {
        return tokenService.removeToken(token);
    }

    /**
     * 检查token是否有效
     * @param token token
     * @return
     */
    @GetMapping(value = "/check_token")
    Boolean checkToken(@RequestParam("token") String token) {
        return tokenService.checkToken(token);
    }

    /**
     * token密码模式授权
     * @param username 用户名
     * @param password 密码
     * @return OAuth2 TokenDTO
     */
    @GetMapping(value = "/token")
    TokenDTO accessToken(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        return tokenService.accessToken(username, password);
    }

    /**
     * 根据token读取AdditionalInformation中username
     * @param token token
     * @return
     */
    @GetMapping(value = "/read_username")
    String readUsername(
            @RequestParam("token") String token
    ) {
        Map<String, Object> map = tokenService.readAdditionalInformation(token);
        return (String) map.get("username");
    }

    /**
     * 无需鉴权
     * @param url url
     * @return
     */
    @GetMapping(value = "/ignore_authentication")
    Boolean ignoreAuthentication(@RequestParam("url") String url) {
        return permissionService.ignoreAuthentication(url);
    }

    /**
     * 是否有权限
     * @param token token
     * @param url url
     * @param method method
     * @return
     */
    @GetMapping(value = "/has_permission")
    Boolean hasPermission(
            @RequestParam("token") String token,
            @RequestParam("url") String url,
            @RequestParam("method") String method
    ) {
        if (tokenService.checkToken(token)) {
            return true;
        }





        // TODO 权限控制

        return true;
    }

}
