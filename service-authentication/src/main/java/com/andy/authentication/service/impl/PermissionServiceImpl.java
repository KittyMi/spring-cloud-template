package com.andy.authentication.service.impl;

import com.andy.authentication.service.IPermissionService;
import com.andy.authentication.service.ITokenService;
import com.andy.core.bean.dto.UserInfoDTO;
import com.andy.core.feign.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author min.lai
 * @date 2019/7/23 15:11
 * desc: 权限服务
 */
@Slf4j
@Service
public class PermissionServiceImpl implements IPermissionService {
    @Resource
    private AntPathMatcher pathMatcher;
    @Resource
    private ITokenService tokenService;
    @Resource
    private IUserService userService;


    /**
     * 无需鉴权
     *
     * @param url url
     * @return
     */
    @Override
    public Boolean ignoreAuthentication(String url) {
        // TODO 放行
        return Stream.of(
                "/service-authentication/**",
                "/service-authorization/**",
                "/service-user/*/login",
                "/**/api-docs",
                "/swagger-ui.html**",
                "/**/druid/**"
        ).anyMatch(
                ignoreUrl -> pathMatcher.match(ignoreUrl, url)
        );

    }

    /**
     * @return
     */

    public Boolean hasPermition(String token, String url, String method){
        Map<String,Object>  map =  tokenService.readAdditionalInformation(token);
        String userName = (String) map.get("username");
        if(userName == null){
            return false;
        }
        UserInfoDTO userInfoDTO = userService.getByUsername(userName);
        // todo 暂时不在这里用，用户在登陆的时候就返回菜单给前端做选择性展示了
       /* if()*/

        return true;

    }

    @Bean
    AntPathMatcher customPathMatcher() {
        AntPathMatcher pathMatcher= new AntPathMatcher();
        pathMatcher.setCachePatterns(true);
        pathMatcher.setCaseSensitive(true);
        pathMatcher.setTrimTokens(true);
        pathMatcher.setPathSeparator("/");
        return pathMatcher;
    }
}
