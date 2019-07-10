package com.bank.bank.Service;


import com.bank.bank.Dao.UserDao;
import com.bank.bank.Models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService  {
    UserDao userDao;

    public User findByName(String username){
        return this.userDao.findByUsername(username);
    }

    public void save(User user){
        try {
             this.userDao.save(user);
        }
        catch (Exception e){
            System.out.println("Error: unUnique");
        }
    }
}
