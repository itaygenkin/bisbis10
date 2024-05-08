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
    @Column(name = "name")
    private String name;
    @ManyToMany
    private final Set<Restaurant> restaurants = new HashSet<>();

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
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

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
