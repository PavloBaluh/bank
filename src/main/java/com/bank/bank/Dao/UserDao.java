package com.bank.bank.Dao;

import com.bank.bank.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    User findByUsername(String username);
}
