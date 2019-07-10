package com.bank.bank.Dao;

import com.bank.bank.Models.Bank_account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Bank_accountDao extends JpaRepository<Bank_account,Integer> {

    Bank_account getByCardNumber(long curdNumber);
}
