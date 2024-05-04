package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Reposiroty.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class ApiController {

    @Autowired
    RestaurantRepo restRepo;

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

    @PostMapping("/restaurants")
    public String addRestaurant(@RequestBody Restaurant rest){
        restRepo.save(rest);
        return "A new restaurant has been added...";
    }

    @GetMapping("/restaurants/{id}")
    public Optional<Restaurant> getById(@PathVariable long id){
        return restRepo.findById(id);
    }
}
