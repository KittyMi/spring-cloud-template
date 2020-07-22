package com.andy.gateway.dto;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author min.lai
 * @date 2019/7/15 17:16
 * desc: 用户DTO对象
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserInfoDTO implements Serializable {
    private static final long serialVersionUID = 6145930493043915878L;

    private Long id;
    private String username;
    private String password;
    private String nickname;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
