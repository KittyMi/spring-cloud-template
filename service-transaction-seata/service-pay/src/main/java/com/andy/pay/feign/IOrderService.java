package com.andy.pay.feign;

import com.andy.core.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author min.lai
 * @version 1.0
 * @date 2020/8/10 19:07
 */
/*@FeignClient(
        value = "service-order",
        configuration = {
                FeignConfig.class
        }
)*/
@FeignClient("service-order")
public interface IOrderService {
    @GetMapping("addOrder")
    String addOrder(@RequestParam("payId") Long payId, @RequestParam("user_id") Long userId);
}
