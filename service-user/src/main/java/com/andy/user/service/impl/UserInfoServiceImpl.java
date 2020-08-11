package com.andy.user.service.impl;

import com.andy.core.bean.dto.TokenDTO;
import com.andy.core.exception.ServiceException;
import com.andy.core.feign.IAuthenticationService;
import com.andy.user.entity.Req;
import com.andy.user.entity.Resp;
import com.andy.user.mybatis.entity.UserInfo;
import com.andy.user.mybatis.mapper.UserInfoMapper;
import com.andy.user.service.IUserInfoService;
import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

import static com.andy.core.bean.ErrorCode.USERNAME_ALREADY_EXIST;


/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Generator
 * @since 2019-07-15
 */
@Slf4j
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Resource
    MapperFacade mapperFacade;
    @Resource
    IAuthenticationService authenticationService;

    /**
     * 注册
     *
     * @param req 注册请求实体
     * @return ResultEntity
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long register(Req.Register req) {
        // 判断用户名是否存在
        String username = req.getUsername();
        UserInfo userInfo = getOne( Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUsername, username) ,false);
        if (Objects.nonNull(userInfo)) {
            throw new ServiceException(USERNAME_ALREADY_EXIST);
        }

        // 注册
        userInfo = mapperFacade.map(req, UserInfo.class);
        userInfo.setPassword( EncryptUtils.md5Base64(req.getPassword()) );
        // 存库
        save(userInfo);

        return userInfo.getId();
    }

    /**
     * 登录
     *
     * @param req 登录实体
     * @return ResultEntity
     */
    @Override
    public Resp.LoginResp login(Req.LoginReq req) {
        TokenDTO dto = authenticationService.accessToken(req.getUsername(), req.getPassword());
        UserInfo userInfo = getOne( Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUsername, req.getUsername()) );
        return new Resp.LoginResp()
                .setNickname(userInfo.getNickName())
                .setAccessToken(dto.getValue())
                .setRefreshToken((String) dto.getRefreshToken().get("value"))
                .setExpiration(dto.getExpiration())
                .setExpiresIn(dto.getExpiresIn())
                .setRefreshExpiration((Long) dto.getRefreshToken().get("expiration"));
    }

    @Override
    public Boolean logout(String token) {
        return authenticationService.removeToken(token);
    }


}
