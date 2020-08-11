package com.andy.pay.feign;

import com.andy.core.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author min.lai
 * @version 1.0
 * @date 2020/8/11 10:50
 */
/*@FeignClient(
        value = "service-account",
        configuration = {
                FeignConfig.class
        }
)*/
@FeignClient("service-account")
public interface IAccountService {
    @GetMapping("deduction")
    String deduction(@RequestParam("userId") Long userId, @RequestParam("money") Integer money);
}
