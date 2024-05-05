package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.Models.Rating;
import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Reposiroty.RatingRepo;
import com.att.tdp.bisbis10.Reposiroty.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class ApiController {

    @Autowired
    RestaurantRepo restRepo;
    @Autowired
    RatingRepo ratingRepo;

    @GetMapping("/")
    public String Welcome(){
        return "Welcome";
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants(){
        return restRepo.findAll();
    }

    @GetMapping("/restaurants?cuisine={cuisine}")
    public List<Restaurant> getByCuisine(@PathVariable String cuisine){
        // TODO: implement
        return null;
    }

    @GetMapping("/restaurants/{id}")
    public Optional<Restaurant> getById(@PathVariable long id){
        return restRepo.findById(id);
    }

    @PostMapping("/restaurants")
    public ResponseEntity addRestaurant(@RequestBody Restaurant rest){
        restRepo.save(rest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/restaurants/{id}")
    public String updateRestaurant(@PathVariable long id){
        // TODO: implement
        return "";
    }

    @DeleteMapping("/restaurants/{id}")
//    @ResponseBody
    public ResponseEntity deleteRestaurant(@PathVariable long id){
        Restaurant toDelete = restRepo.getById(id);
        restRepo.delete(toDelete);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/ratings")
    public List<Rating> rateRestaurant(@RequestParam long restaurantId, @RequestParam float rating){
        if (restRepo.findById(restaurantId).isEmpty())
            return null;
        Restaurant restaurant = restRepo.findById(restaurantId).get();
        if (restaurant.ratingIsNull()){
            Rating rate = new Rating(restaurant, rating);
            ratingRepo.saveAndFlush(rate);
            restaurant.setRating(rate);
        }
        else{
            restaurant.addRating(rating);
            restRepo.save(restaurant);
        }
        return ratingRepo.findAll();
    }

}
