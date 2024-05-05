package com.att.tdp.bisbis10.Models;

import jakarta.persistence.*;

@Entity
@Table(name="Ratings", uniqueConstraints = {@UniqueConstraint(columnNames = {"restId"})})
public class Rating {

    /**************FIELDS*****************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @PrimaryKeyJoinColumn(referencedColumnName = "restId")
    private Restaurant restaurant;
    @Column(nullable = false)
    private Float rating = 0.0F;
    @Column
    private Float numOfRatings = 0.0F;

    /**************Constructors*****************/
    // empty constructor
    public Rating() {}

    public Rating(Restaurant rest){
        this.restaurant = rest;
    }

    public Rating(Restaurant rest, Float rating){
        this.restaurant = rest;
        this.rating = rating;
        this.numOfRatings = 1.0F;
    }

    /****************Methods*******************/
    public Restaurant getRestId() { return this.restaurant; }
    public void setRestId(long id) { this.restaurant.setId(id);}
    public Float getRating() { return this.rating; }
    public void setRating(Float rating) { this.rating = rating; }
    public Float getNumOfRatings() { return this.numOfRatings; }
    public void setNumOfRatings(Float ratings) { this.numOfRatings = ratings; }

    public void addRating(Float rating){
        if (rating < 0 || rating > 5) // do nothing
            return;
        setNumOfRatings((getRating() * getNumOfRatings() + rating) / (getNumOfRatings() + 1));
        setNumOfRatings(getNumOfRatings() + 1);
    }
    public void setId(Long id) { this.id = id; }
    public Long getId() { return this.id; }
}
