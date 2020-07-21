package com.andy.user.entity;

import com.andy.core.bean.ImABean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author ly
 * @date 2020/5/28 11:41
 */
public class Req {

    /**
     * 登录
     * 类添加Req纯粹是为了Swagger显示
     */
    @ToString
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class LoginReq implements ImABean {
        private static final long serialVersionUID = -8489794471621298907L;
        @ApiModelProperty(notes = "用户名,字母数字3-20位", required = true)
       // @Pattern( regexp = "^[a-zA-Z0-9]{3,20}$", message = "101001" )
        private String username;

        @ApiModelProperty(notes = "密码,字母数字6-20位", required = true)
        //@Pattern( regexp = "^[a-zA-Z0-9]{6,20}$", message = "101002" )
        private String password;

        @ApiModelProperty(notes = "登录方式", example = "web")
        private String way;
    }

    /**
     * 注册
     */
    @ToString
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Register implements ImABean {
        private static final long serialVersionUID = -4975826369370600044L;
        @ApiModelProperty(notes = "用户名,字母数字3-20位", required = true)
        //@Pattern( regexp = "^[0-9]{11}$", message = "101001" )
        private String username;

        @ApiModelProperty(notes = "密码,字母数字6-20位", required = true)
        //@Pattern( regexp = "^[a-zA-Z0-9]{6,20}$", message = "101002" )
        private String password;

        @ApiModelProperty(notes = "昵称", required = false)
        private String nickName;

        @ApiModelProperty(notes = "组织机构编码", required = false)
        private Integer organId;

//        @ApiModelProperty(notes = "邮箱")
//        @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "101005")
//        private String email;
//
//        @Pattern(regexp = "^1(3[0-9]|5[012356789]|8[0256789]|7[0678])\\d{8}$", message = "101006")
////        @Pattern(regexp = "^\\d{11}$", message = "101006")
//        private String phone;

    }
}
