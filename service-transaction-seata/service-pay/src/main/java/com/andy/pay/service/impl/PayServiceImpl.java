package com.andy.pay.service.impl;

import com.andy.pay.config.SnowFlake;
import com.andy.pay.feign.IAccountService;
import com.andy.pay.feign.IOrderService;
import com.andy.pay.mybatis.entity.Pay;
import com.andy.pay.mybatis.mapper.PayMapper;
import com.andy.pay.service.PayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author min.lai
 * @version 1.0
 * @date 2020/8/10 19:01
 */
@Service
public class PayServiceImpl extends ServiceImpl<PayMapper, Pay> implements PayService {


    @Resource
    IAccountService accountService;
    @Resource
    IOrderService orderService;
    @Resource
    SnowFlake snowFlake;


    @Override
    @GlobalTransactional
    public String addPay(Long userId, Integer money) {
        Long payId=snowFlake.nextKey();
        //添加支付信息
        baseMapper.insert(new Pay().setId(payId).setMoney(money).setUserId(userId));
        //扣除账号金额
        accountService.deduction(userId, money);
        //创建订单
        orderService.addOrder(payId,userId);
        //模拟异常
        int a = 1/0;
        return "success";
    }
}
