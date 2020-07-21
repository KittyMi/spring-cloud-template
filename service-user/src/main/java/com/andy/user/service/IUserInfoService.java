package com.andy.user.service;

import com.andy.mybatis.entity.UserInfo;
import com.andy.user.entity.Req;
import com.andy.user.entity.Resp;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Generator
 * @since 2019-07-15
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 注册
     * @param req 注册请求实体
     * @return ResultEntity
     */
    Long register(Req.Register req);

    /**
     * 登录
     * @param req 登录实体
     * @return ResultEntity
     */
    Resp.LoginResp login(Req.LoginReq req);


}
