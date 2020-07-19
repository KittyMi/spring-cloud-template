package com.andy.core.feign;

import com.andy.core.bean.dto.UserInfoDTO;
import com.andy.core.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * @author ly
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
    @RequestMapping(value = "/user_info/get_by_user_id", method = RequestMethod.GET)
    UserInfoDTO getByUserId(@RequestParam(value = "userId") Long userId);




}


