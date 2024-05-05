package com.att.tdp.bisbis10.Models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Restaurants")
public class Restaurant {

    /**************FIELDS*****************/
    @Id
    @Column(name = "restId",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean isKosher;
    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "restRating",referencedColumnName = "rating")
    private Rating rating;
    @OneToMany
    @JoinColumn(name = "dishes")
    private Set<Dish> dishList = new HashSet<>();

    /**************Methods*****************/
    public long getId(){ return this.id; }
    public void setId(long id) { this.id = id; }
    public String getName(){ return this.name; }
    public void setName(String name) { this.name = name; }
    public boolean getIsKosher(){ return this.isKosher; }
    public void setIsKosher(boolean kosher) { this.isKosher = kosher; }
    public Float getRating(){
        if (ratingIsNull())
            return 0.0F;
        else
            return this.rating.getRating();
    }
    public void setRating(Rating rating) { this.rating = rating; }
    public Set<Dish> getDishList() { return this.dishList; }
    public void setDishList(Set<Dish> dishes) {this.dishList = dishes; }
    public void addRating(Float rate) {
        if (ratingIsNull())
            System.out.println("rating is null");
        else
            this.rating.addRating(rate);
    }
    public boolean ratingIsNull(){ return this.rating == null; }
    public void addDish(Dish dish){
        this.dishList.add(dish);
    }

}
