package com.andy.order.service;

import com.andy.core.exception.ServiceException;
import com.andy.order.mybatis.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author min.lai
 * @version 1.0
 * @date 2020/8/10 19:00
 */
public interface OrderService extends IService<Order> {
    String add(Long payId, Long userId);
}
