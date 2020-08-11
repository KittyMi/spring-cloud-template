package com.andy.order.controller;

import com.andy.core.annotation.CurrentUser;
import com.andy.core.bean.dto.UserInfoDTO;
import com.andy.core.exception.ServiceException;
import com.andy.order.service.OrderService;
import com.baomidou.mybatisplus.extension.api.ApiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author min.lai
 * @version 1.0
 * @date 2020/8/10 19:02
 */
@RestController
public class OrderController extends ApiController {
    @Resource
    private OrderService orderService;

    @GetMapping("addOrder")
    public String addOrder(@RequestParam("payId") Long payId,@RequestParam("user_id") Long userId) {
        return orderService.add(payId,userId);
    }
}
