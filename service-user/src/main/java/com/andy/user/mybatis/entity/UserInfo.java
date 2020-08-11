package com.andy.user.mybatis.entity;

import com.andy.mybatis.entity.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
    * 用户表
    * </p>
*
* @author Generator
* @since 2020-03-01
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_info")
public class UserInfo extends BaseDO {

    private static final long serialVersionUID = 1L;
        /**
        * 用户名
        */
    private String username;

        /**
        * 密码
        */
    private String password;

        /**
        * 邮箱
        */
    private String email;

        /**
        * 手机
        */
    private String phone;

        /**
        * 用户昵称
        */
    private String nickName;

        /**
        * 删除标志
        */
    @TableLogic
    private Integer isDeleted;

}
