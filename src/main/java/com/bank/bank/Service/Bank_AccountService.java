package com.bank.bank.Service;

import com.bank.bank.Dao.Bank_accountDao;
import com.bank.bank.Models.Bank_account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bank_AccountService {
    @Autowired
    private Bank_accountDao bank_accountDao;

    public void save(Bank_account bank_account){
        bank_accountDao.save(bank_account);
    }

    public Bank_account getByCurdNumber(long number){
        return bank_accountDao.getByCardNumber(number);
    }
}
