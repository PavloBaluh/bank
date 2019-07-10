package com.bank.bank.Models;

import com.bank.bank.Service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@ToString(exclude = "bank_account")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column( name = "username", unique = true)
    private String username;
    private String password;
    @OneToOne( fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private  Bank_account bank_account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
