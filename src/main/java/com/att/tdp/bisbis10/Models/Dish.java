package com.att.tdp.bisbis10.Models;

import jakarta.persistence.*;

@Entity
public class Dish {

    /**************FIELDS*****************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private int price;

    /***************Constructors*******************/
    public Dish() {}

    public Dish(String name, String desc, int price){
        this.name = name;
        this.description = desc;
        this.price = price;
    }

    /****************Methods*******************/
    public void setId(Long id) { this.id = id; }
    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return this.description; }
    public void setDescription(String desc) { this.description = desc; }
    public int getPrice() { return this.price; }
    public void setPrice(int price) { this.price = price; }

}
