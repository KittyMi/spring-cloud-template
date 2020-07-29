package com.andy.authorization.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义清空token
 * @author min.lai
 */
@RestController
@RequestMapping("auth")
public class DefaultController {
    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;

    /**
     * token清空
     * @param token
     * @return
     */
    @GetMapping("remove_token")
    Boolean removeToken(@RequestParam("token") String token){
        return consumerTokenServices.revokeToken(token);
    }
}
