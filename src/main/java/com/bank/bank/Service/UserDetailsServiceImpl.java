package com.bank.bank.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "user")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByName(username);
    }
}

