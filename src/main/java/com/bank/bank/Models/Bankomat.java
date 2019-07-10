package com.bank.bank.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Bankomat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int note500;
    private int note200;
    private int note100;
}
