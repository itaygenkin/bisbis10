package com.att.tdp.bisbis10.Models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Cuisines")
public class Cuisine {

    /*****************FIELDS*****************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(referencedColumnName = "restId", unique = false)
    private Set<Restaurant> restaurants = new HashSet<>();

    /****************Constructors*****************/
    public Cuisine() {}

    public Cuisine(String name){
        this.name = name;
    }

    public Cuisine(String name, Restaurant restaurant){
        this.name = name;
        this.restaurants.add(restaurant);
    }

    /*****************Methods*******************/
    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }

    public Set<Restaurant> GetRestaurants(){
        return this.restaurants;
    }

    public void addRestaurant(Restaurant restaurant){
        this.restaurants.add(restaurant);
    }

    public void removeRestaurant(Restaurant restaurant){
        this.restaurants.remove(restaurant);
    }
}
