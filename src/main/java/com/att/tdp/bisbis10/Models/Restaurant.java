package com.att.tdp.bisbis10.Models;

import com.att.tdp.bisbis10.Records.RestaurantRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name="Restaurants")
public class Restaurant {

    /****************FIELDS*****************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "averageRating")
    private Float averageRating = 0.0F;
    @Column(name = "isKosher",nullable = false)
    private boolean isKosher;
    @JsonIgnore
    private float numOfRatings = 0.0F;
    @JsonIgnore
    private final Integer ratingLocker = 0;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dishes")
    private Set<Dish> dishes = new HashSet<>();
    @ElementCollection
    private Collection<String> cuisines = new HashSet<>();


    /****************Constructors*****************/
    public Restaurant() {}
    public Restaurant(String name, boolean kosher){
        this.name = name;
        this.isKosher = kosher;
    }
    public Restaurant(String name, boolean kosher, Collection<String> cuisines){
        this.name = name;
        this.isKosher = kosher;
        this.cuisines.addAll(cuisines);
    }

    public Restaurant(RestaurantRecord restaurantRecord){
        this.name = restaurantRecord.name();
        this.isKosher = restaurantRecord.isKosher();
        this.cuisines = restaurantRecord.cuisines();
    }

    /****************Methods*****************/
    public long getId(){ return this.id; }
    public void setId(long id) { this.id = id; }
    public String getName(){ return this.name; }
    public void setName(String name) { this.name = name; }
    public boolean getIsKosher(){ return this.isKosher; }
    public void setIsKosher(boolean kosher) { this.isKosher = kosher; }
    public Float getAverageRating(){
        if (this.numOfRatings == 0)
            return 0.0F;
        float res = this.averageRating;
        return (float) (Math.round(res * 100) / 100.0);
    }
    public void rateRestaurant(float val){
        synchronized (ratingLocker){
            this.averageRating = (averageRating * numOfRatings + val) / (numOfRatings + 1);
            this.numOfRatings ++;
        }
    }

    public Set<Dish> getDishes() { return this.dishes; }
    public void setDishes(Set<Dish> dishes) { this.dishes = dishes; }
    public void addDish(Dish dish){
        this.dishes.add(dish);
    }
    public void removeDish(Dish dish){
        this.dishes.remove(dish);
    }
    public void setCuisines(Collection<String> cuisines){ this.cuisines = cuisines; }
    public Collection<String> getCuisines() { return this.cuisines; }
    public void addCuisines(Collection<String> cuisines){
        this.cuisines.addAll(cuisines);
    }

    public boolean containsCuisine(String cuisine){
        return this.cuisines.contains(cuisine);
    }

    public boolean containsDish(long dishId) {
        Set<Dish> dishList = this.dishes; // preventing concurrent read-write issues
        for (Dish d : dishList){
            if (d.getId() == dishId)
                return true;
        }
        return false;
    }
}
