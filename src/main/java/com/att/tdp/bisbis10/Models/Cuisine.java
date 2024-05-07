package com.att.tdp.bisbis10.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Cuisines")
public class Cuisine {

    /*****************FIELDS*****************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(referencedColumnName = "restId", unique = false)
//    private Restaurant restaurant;

    /****************Constructors*****************/
    public Cuisine() {}

    public Cuisine(String name){
        this.name = name;
    }

//    public Cuisine(String name, Restaurant restaurant){
//        this.name = name;
//        this.restaurant = restaurant;
//    }

    /*****************Methods*******************/
    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
}
