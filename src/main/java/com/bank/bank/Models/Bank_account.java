package com.bank.bank.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude = "user")
@AllArgsConstructor
@NoArgsConstructor
public class Bank_account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private double money;
    private long cardNumber;
    @OneToOne( fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "bank_account")
    private User user;
}
