package com.andy.user.controller;

import com.andy.core.bean.dto.UserInfoDTO;
import com.andy.user.mybatis.entity.UserInfo;
import com.andy.user.service.IUserInfoService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author ly
 * @date 2019/8/2 11:31
 * desc: 微服务间调用
 */
@Slf4j
@RestController
@RequestMapping(value = "/user_info")
@ApiIgnore
public class FeignController {

    @Resource
    IUserInfoService service;
    @Resource
    MapperFacade mapperFacade;

    /**
     * 通过用户名称查询UserInfoDTO
     *
     * @param username 用户名
     * @return UserInfoDTO
     */
    @GetMapping(value = "/get_by_username")
    UserInfoDTO getByUsername(
            @RequestParam(value = "username") String username
    ) {
        UserInfo userInfo = service.getOne(
                Wrappers.<UserInfo>lambdaQuery().eq(
                        UserInfo::getUsername, username
                )
        );

        return mapperFacade.map(
                userInfo,
                UserInfoDTO.class
        );
    }


    /**
     * 通过userId查询UserInfoDTO
     *
     * @param userId 用户名
     * @return UserInfoDTO
     */
    @GetMapping(value = "/get_by_id")
    UserInfoDTO getByUserId(
            @RequestParam(value = "userId") Long userId
    ) {
        UserInfo userInfo = service.getById(userId);

        return mapperFacade.map(
                userInfo,
                UserInfoDTO.class
        );
    }

}
