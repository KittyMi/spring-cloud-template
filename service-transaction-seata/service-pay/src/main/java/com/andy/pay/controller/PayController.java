package com.andy.pay.controller;

import com.andy.core.annotation.CurrentUser;
import com.andy.core.bean.dto.UserInfoDTO;
import com.andy.pay.service.PayService;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
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
public class PayController extends ApiController {
    @Resource
    private PayService payService;

    @GetMapping("pay")
    public R pay(@CurrentUser UserInfoDTO dto, @RequestParam("money") Integer money){
        return success(payService.addPay(dto.getId(), money));
    }

}
