package com.att.tdp.bisbis10.Models;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name="Restaurants")
public class Restaurant {

    /**************FIELDS*****************/
    @Id
    @Column(name = "restId",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "isKosher",nullable = false)
    private boolean isKosher;
    @Column(name = "averageRating")
    private Float averageRating = 0.0F;
    @OneToMany
    @JoinColumn(name = "dishes")
    private List<Dish> dishes = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Cuisine> cuisines = new HashSet<>();

    private float numOfRating = 0.0F;

    /****************Constructors*****************/
    public Restaurant() {}
    public Restaurant(String name, boolean kosher, Set<Cuisine> cuisines){
        this.name = name;
        this.isKosher = kosher;
        this.cuisines = cuisines;
    }

    /**************Methods*****************/
    public long getId(){ return this.id; }
    public void setId(long id) { this.id = id; }
    public String getName(){ return this.name; }
    public void setName(String name) { this.name = name; }
    public boolean getIsKosher(){ return this.isKosher; }
    public void setIsKosher(boolean kosher) { this.isKosher = kosher; }
    public List<Dish> getDishes() { return this.dishes; }
    public void setDishes(List<Dish> dishes) { this.dishes = dishes; }
    public void addDish(Dish dish){
        this.dishes.add(dish);
    }
    public void removeDish(Dish dish){
        this.dishes.remove(dish);
    }
    public void setCuisines(Set<Cuisine> cuisines){ this.cuisines = cuisines; }
    public Set<String> getCuisines() {
        if (this.cuisines == null)
            return new HashSet<>();

        return this.cuisines.stream().map(Cuisine::getName).collect(Collectors.toSet());
    }
    public Set<Cuisine> GetCuisineList() {
        if (this.cuisines == null)
            return new HashSet<>();

        return this.cuisines;
    }
    public void addCuisine(Cuisine cuisine) {
        this.cuisines.add(cuisine);
    }
    public void removeCuisine(Cuisine cuisine) {
        this.cuisines.remove(cuisine);
    }

    public float getAverageRating() { return this.averageRating; }
    public void setAverageRating(float rating) { this.averageRating = rating; }

    public void addRating(float rating) {
        this.setAverageRating((this.averageRating * this.numOfRating + rating) / (this.numOfRating + 1));
        this.numOfRating++;
    }

    public void addCuisines(Collection<Cuisine> cuisines){
        this.cuisines.addAll(cuisines);
    }

    public boolean containsCuisine(Cuisine cuisine){
        for (Cuisine c : this.cuisines){
            if (c.getName().equals(cuisine.getName()))
                return true;
        }
        return false;
    }

}
