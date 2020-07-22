package com.andy.gateway.feign;

import com.andy.gateway.config.FeignConfig;
import com.andy.gateway.dto.UserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author min.lai
 * @date 2019/7/18 18:02
 * desc: FeignClient
 */
@FeignClient(
        value = "service-user",
        configuration = {
                FeignConfig.class
        }
)
public interface IUserService {

    /**
     * 通过username查询
     * @param username 用户名
     * @return UserInfoDTO
     */
    @RequestMapping(value = "/user_info/get_by_username", method = RequestMethod.GET)
    UserInfoDTO getByUsername(@RequestParam(value = "username") String username);

    /**
     * 通过userId查询
     * @param userId userId
     * @return UserInfoDTO
     */
    @RequestMapping(value = "/user_info/get_by_id", method = RequestMethod.GET)
    UserInfoDTO getById(@RequestParam(value = "userId") Long userId);
}


