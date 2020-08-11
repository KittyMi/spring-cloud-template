package com.andy.authorization.config;

import com.andy.core.bean.dto.UserInfoDTO;
import com.andy.core.feign.IUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.andy.core.bean.ErrorCode.USERNAME_NOT_FOUND;

/**
 * @author min.lai
 * @date 2019/7/17 19:52
 * desc: Jdbc user detail
 */
public class JdbcUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserInfoDTO userInfo=userService.getByUsername(s);

        if (null == userInfo) {
            throw new UsernameNotFoundException(String.valueOf(USERNAME_NOT_FOUND.getCode()));
        }

        // TODO
        List<String> roleList = new ArrayList<>();
        roleList.add("TEST");

        List<GrantedAuthority> list = roleList.stream().map(
                SimpleGrantedAuthority::new
        ).collect(Collectors.toList());

        return new User(
                s, userInfo.getPassword(),
                list
        );
    }
}
