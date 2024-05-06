package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.Models.Dish;
import com.att.tdp.bisbis10.Models.Rating;
import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Reposiroty.DishRepo;
import com.att.tdp.bisbis10.Reposiroty.RatingRepo;
import com.att.tdp.bisbis10.Reposiroty.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class ApiController {

    @Autowired
    RestaurantRepo restRepo;
    @Autowired
    RatingRepo ratingRepo;
    @Autowired
    DishRepo dishRepo;

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
    public ResponseEntity deleteRestaurant(@PathVariable long id){
        Restaurant toDelete = restRepo.getById(id);
        restRepo.delete(toDelete);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/ratings")
    public void rateRestaurant(@RequestParam long restaurantId, @RequestParam float rating){
        if (restRepo.findById(restaurantId).isEmpty())
            return;
        Restaurant restaurant = restRepo.findById(restaurantId).get();
        if (restaurant.ratingIsNull()){
            Rating rate = new Rating(restaurant, rating);
            ratingRepo.saveAndFlush(rate);
            restaurant.setRating(rate);
        }
        else{
            restaurant.addRating(rating);
            restRepo.saveAndFlush(restaurant);
        }
    }

    @PostMapping("/restaurants/{id}/dishes")
    public ResponseEntity addDish(@PathVariable long id, @RequestParam String name,
                                  @RequestParam String description, @RequestParam int price){
        if (restRepo.findById(id).isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Restaurant restaurant = restRepo.findById(id).get();
        Dish dish = new Dish(name, description, price);
        dishRepo.save(dish);
        restaurant.addDish(dish);
        restRepo.save(restaurant);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/restaurants/{id}/dishes/{dishId}")
    public void updateDish(@PathVariable long id, @PathVariable long dishId,
                      @RequestParam String description, @RequestParam int price){
        if (restRepo.findById(id).isEmpty() || dishRepo.findById(dishId).isEmpty())
            return; // do nothing
        Dish updatedDish = dishRepo.findById(dishId).get();
        updatedDish.setDescription(description);
        updatedDish.setPrice(price);
        dishRepo.save(updatedDish);
    }

    @DeleteMapping("/restaurants/{id}/dishes/{dishId}")
    public ResponseEntity deleteDish(@PathVariable long id, @PathVariable long dishId){
        if (restRepo.findById(id).isPresent() &&
                dishRepo.findById(dishId).isPresent()){
            Restaurant restaurant = restRepo.findById(id).get();
            Dish dish = dishRepo.findById(dishId).get();
            restaurant.removeDish(dish);
            dishRepo.delete(dish);
            restRepo.save(restaurant);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/restaurants/{id}/dishes")
    public List<Dish> getDishes(@PathVariable long id){
        if (restRepo.findById(id).isEmpty())
            return new ArrayList<>();
        return restRepo.findById(id).get().getDishList();
    }

}
