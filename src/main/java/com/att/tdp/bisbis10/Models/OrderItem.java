package com.att.tdp.bisbis10.Models;

import jakarta.persistence.*;

@Embeddable
public class OrderItem {

    public Long dishId;
    public Integer amount;

    public OrderItem(){}

    public OrderItem(Long dishId, Integer amount){
        this.dishId = dishId;
        this.amount = amount;
    }

    public long getDishId() { return this.dishId; }
    public void setDishId(Long id) { this.dishId = id; }
    public int getAmount() { return this.amount; }
    public void setAmount(int amount) { this.amount = amount; }
}
