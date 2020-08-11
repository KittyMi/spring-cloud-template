package com.andy.pay.service;

import com.andy.pay.mybatis.entity.Pay;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author min.lai
 * @version 1.0
 * @date 2020/8/10 19:00
 */
public interface PayService extends IService<Pay> {
    String addPay(Long userId, Integer money);
}
