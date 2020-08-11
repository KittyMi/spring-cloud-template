package com.andy.pay.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author min.lai
 * @version 1.0
 * @date 2020/8/10 18:57
 */
@TableName("t_pay")
@Data
@Accessors(chain = true)
public class Pay implements Serializable {
    @TableId(type = IdType.INPUT)
    Long id;
    Integer money;
    Long userId;
}
