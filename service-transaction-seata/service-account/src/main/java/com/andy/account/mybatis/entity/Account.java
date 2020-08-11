package com.andy.account.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author min.lai
 * @version 1.0
 * @date 2020/8/11 9:33
 */
@Data
@TableName("t_account")
public class Account implements Serializable {
    private Long id;
    private Long userId;
    private Integer money;
}
