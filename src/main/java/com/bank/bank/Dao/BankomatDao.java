package com.bank.bank.Dao;

import com.bank.bank.Models.Bankomat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankomatDao extends JpaRepository<Bankomat,Integer> {
}
