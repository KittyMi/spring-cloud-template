package com.andy.account.service;

import com.andy.account.mybatis.entity.Account;
import com.andy.core.exception.ServiceException;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AccountService extends IService<Account> {

    String deduction(Long userId, Integer money);
}
