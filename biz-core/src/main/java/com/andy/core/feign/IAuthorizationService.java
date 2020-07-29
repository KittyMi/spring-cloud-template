package com.andy.core.feign;

import com.andy.core.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

/**
 * @author min.lai
 * @date 2019/7/19 17:51
 * desc: FeignClient Auth Service
 */
@FeignClient(
        value = "service-authorization",
        configuration = {
                FeignConfig.class
        }
)
public interface IAuthorizationService {


        /**
         * token删除
         * @param token
         * @return
         */
        @RequestMapping(
                value = "/auth/remove_token",
                method = RequestMethod.GET
        )
        Boolean removeToken(@RequestParam("token") String token);
        /**
         * OAuth2 Server check token
         * @param token token
         * @return Map
         */
        @RequestMapping(
                value = "/auth/check_token",
                method = RequestMethod.GET
        )
        Map<String, Object> checkToken(@RequestParam("token") String token);

        /**
         * OAuth2 Server授权token
         * @param principal
         * @param parameters map
         * @return map
         */
        @RequestMapping(
                value = "/auth/token",
                method = RequestMethod.POST
        )
        Map<String, Object> accessToken(
                Principal principal,
                @RequestParam
                Map<String, String> parameters
        );
}
