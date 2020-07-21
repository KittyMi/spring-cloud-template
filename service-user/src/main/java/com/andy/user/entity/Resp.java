package com.andy.user.entity;

import com.andy.core.bean.ImABean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author ly
 * @date 2020/5/28 11:42
 */
public class Resp {

    /**
     * 登录返回值
     * 类添加Resp纯粹是为了Swagger显示
     */
    @ToString
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class LoginResp implements ImABean {
        private static final long serialVersionUID = -9109406528304896750L;

        @ApiModelProperty(notes = "访问token")
        private String accessToken;
        @ApiModelProperty(notes = "刷新token")
        private String refreshToken;
        @ApiModelProperty(notes = "访问token多久过期")
        private Integer expiresIn;
        @ApiModelProperty(notes = "访问token过期时刻")
        private Long expiration;
        @ApiModelProperty(notes = "刷新token过期时刻")
        private Long refreshExpiration;
        @ApiModelProperty(notes = "用户名称")
        private String nickname;
    }
}
