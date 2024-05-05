package com.att.tdp.bisbis10.Models;

import jakarta.persistence.*;

@Entity
public class Restaurant {

    /**************FIELDS*****************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private boolean isKosher;
    @Column
    private Float rating = 0F;

    private Float numOfRates = 0F;

    /**************Methods*****************/
    public long getId(){ return this.id; }
    public void setId(long id) { this.id = id; }
    public String getName(){ return this.name; }
    public void setName(String name) { this.name = name; }
    public boolean getIsKosher(){ return this.isKosher; }
    public void setIsKosher(boolean kosher) { this.isKosher = kosher; }
    public float getRating(){ return this.rating; }
    public void setRating(float rate) {
        if (rate < 0 || rate > 5)
            return;
        this.rating = (this.rating * this.numOfRates + rate) / (this.numOfRates + 1);
        this.setNumOfRates(this.numOfRates + 1);
    }
    public float getNumOfRates() { return this.numOfRates; }
    public void setNumOfRates(Float numOfRates) { this.numOfRates = numOfRates; }

}
