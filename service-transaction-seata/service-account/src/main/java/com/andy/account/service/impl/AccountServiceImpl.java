package com.andy.account.service.impl;


import com.andy.account.mybatis.entity.Account;
import com.andy.account.mybatis.mapper.AccountMapper;
import com.andy.account.service.AccountService;
import com.andy.core.exception.ServiceException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author min.lai
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper,Account> implements AccountService {

    @Override
    public String deduction(Long userId, Integer money) {
        int haveMoney = this.getById(userId).getMoney();
        if(haveMoney < money){
            throw new ServiceException("金额不足");
        }
        this.lambdaUpdate()
                .set(Account::getMoney,haveMoney - money)
                .eq(Account::getId,userId)
                .update();
        return "success";
    }
}
