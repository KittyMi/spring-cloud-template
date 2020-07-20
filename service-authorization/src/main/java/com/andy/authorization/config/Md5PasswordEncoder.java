package com.andy.authorization.config;

import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author min.lai
 * @date 2019/7/17 19:45
 * desc: MD5 base64加密
 */
@Slf4j
public class Md5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return EncryptUtils.md5Base64(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return EncryptUtils.md5Base64(charSequence.toString()).equals(s);
    }
}
