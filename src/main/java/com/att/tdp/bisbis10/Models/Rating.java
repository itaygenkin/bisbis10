package com.att.tdp.bisbis10.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;

@Entity
public class Rating {

    @Column
    @ForeignKey
    private long restId;
    @Column
    private Float rating = 0.0F;
    @Column
    private Float numOfRatings = 0.0F;

    public long getRestId() { return this.restId; }
    public void setRestId(long id) { this.restId = id;}
    public Float getRating() { return this.rating; }
    public void setRating(Float rating) { this.rating = rating; }
    public Float getNumOfRatings() { return this.numOfRatings; }
    public void setNumOfRatings(Float ratings) { this.numOfRatings = ratings; }

    public void addRating(Float rating){
        if (rating < 0 || rating > 5) // do nothing
            return;
        Float updatedRating = (getRating() * getNumOfRatings() + rating) / (getNumOfRatings() + 1);
        setRating(updatedRating);
        setNumOfRatings(getNumOfRatings() + 1);
    }
}
