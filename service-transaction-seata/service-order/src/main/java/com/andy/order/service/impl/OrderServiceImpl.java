package com.andy.order.service.impl;

import com.andy.order.config.SnowFlake;
import com.andy.order.mybatis.entity.Order;
import com.andy.order.mybatis.mapper.OrderMapper;
import com.andy.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author min.lai
 * @version 1.0
 * @date 2020/8/10 19:01
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    SnowFlake snowFlake;

    @Override
    public String add(Long payId, Long userId){
        this.save(new Order().setId(snowFlake.nextKey()).setPayId(payId).setUserId(userId));
        //模拟异常
        int a = 1/0;
        return "success";
    }
}
