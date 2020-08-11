package com.andy.account.controller;

import com.andy.account.service.AccountService;
import com.andy.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("deduction")
    public String deduction(Long userId, Integer money) {
        return accountService.deduction(userId, money);
    }
}
