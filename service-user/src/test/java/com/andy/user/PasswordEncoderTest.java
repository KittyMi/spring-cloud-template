package com.andy.user;


import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;

/**
 * Unit test for simple App.
 */
public class PasswordEncoderTest
{
    public static void main(String[] args) {
        System.out.println(EncryptUtils.md5Base64("test"));
    }
}
