package com.andy.user.controller;


import com.andy.core.annotation.CurrentUser;
import com.andy.core.bean.Result;
import com.andy.core.bean.ResultFactory;
import com.andy.core.bean.dto.UserInfoDTO;
import com.andy.user.entity.Req;
import com.andy.user.entity.Resp;
import com.andy.user.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Generator
 * @since 2019-07-15
 */
@Slf4j
@RestController
@RequestMapping(value = "/user_info")
@Api(tags = "用户")
public class UserInfoController {

    @Resource
    IUserInfoService service;


    /**
     * 注册
     * @param req 注册请求实体
     * @return ResultEntity
     */
    @PostMapping(value = "/register")
    @ApiOperation(value = "用户注册")
    Result register(
            @RequestBody @Valid Req.Register req
    ) {
        return ResultFactory.success(service.register(req));
    }

    /**
     * 登录
     * @param req LoginReq.Req
     * @return ResultEntity
     */
    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录")
    Result<Resp.LoginResp> login(
            @RequestBody @Valid Req.LoginReq req
    ) {
        return ResultFactory.success(service.login(req));
    }

    /**
     * 登出
     * @param
     * @return
     */
    @GetMapping(value = "/logout")
    @ApiOperation(value = "用户登出")
    Result<Boolean> logout(String accessToken) {
        return ResultFactory.success(service.logout(accessToken));
    }

    /**
     * 用户列表
     * @return List<UserInfoDTO>
     */
    @GetMapping("/list")
    @ApiOperation(value = "用户列表")
    Result list(
            @CurrentUser UserInfoDTO dto
    ) {
        log.info(dto.toString());
        return ResultFactory.success(service.list());
    }
}
