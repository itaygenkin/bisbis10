package com.att.tdp.bisbis10.Models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Orders")
public class Order {

    /**************FIELDS*****************/
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;
    @Column(name = "restId")
    private long restaurantId;
    @Column(name = "dishPair")
    @OneToMany
    private List<OrderItem> orderItems;

    /****************Constructors*****************/
    public Order(){}
    public Order(long id, List<OrderItem> order){
        this.restaurantId = id;
        this.orderItems = order;
    }

    /*****************Methods*******************/
    public void setOrderId(UUID id) { this.orderId = id; }
    public UUID getOrderId() { return this.orderId; }
//    public long getRestaurantId() { return this.restaurantId; }
    public void setRestaurantId(long id) { this.restaurantId = id; }
    public List<OrderItem> GetOrderItems() { return this.orderItems; }
    public void setOrderItems(List<OrderItem> order) { this.orderItems = order; }

}
