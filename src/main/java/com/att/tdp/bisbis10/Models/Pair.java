package com.att.tdp.bisbis10.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pair {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public Long dishId;
    public Integer amount;

    public Pair(){}

    public Pair(Long dishId, Integer amount){
        this.dishId = dishId;
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
