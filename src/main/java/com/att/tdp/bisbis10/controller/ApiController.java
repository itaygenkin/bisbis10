package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.Models.Cuisine;
import com.att.tdp.bisbis10.Models.Dish;
import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Order;
import com.att.tdp.bisbis10.Pair;
import com.att.tdp.bisbis10.Reposiroty.CuisineRepo;
import com.att.tdp.bisbis10.Reposiroty.DishRepo;
import com.att.tdp.bisbis10.Reposiroty.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
public class ApiController {

    @Autowired
    RestaurantRepo restRepo;
    @Autowired
    DishRepo dishRepo;
    @Autowired
    CuisineRepo cuisineRepo;

    @GetMapping("/")
    public String Welcome(){
        return "Welcome to bisbis10";
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants(@RequestParam Optional<String> cuisine){
        if (cuisine.isEmpty())
            return restRepo.findAll();

        Cuisine exampleCuisine = new Cuisine(cuisine.get());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        Example<Cuisine> example = Example.of(exampleCuisine, matcher);
        List<Cuisine> cuisines = cuisineRepo.findAll(example);

        List<Restaurant> restaurants = new ArrayList<>();
        for (Cuisine c : cuisines)
            restaurants.addAll(c.GetRestaurants());

        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Optional<Restaurant> getById(@PathVariable long id){
        return restRepo.findById(id);
    }

    @PostMapping("/restaurants")
    public ResponseEntity addRestaurant(@RequestBody Restaurant rest){
        Set<Cuisine> cuisines = rest.GetCuisineList();
        for (Cuisine c : cuisines)
            c.addRestaurant(rest);
        cuisineRepo.saveAll(cuisines);
        restRepo.save(rest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/restaurants/{id}")
    public void updateRestaurant(@PathVariable long id,
                                 @RequestParam(value="cuisines") String[] cuisines
    ){
        if (restRepo.findById(id).isEmpty() || cuisines.length == 0)
            return;

        Restaurant restaurant = restRepo.findById(id).get();
        List<Cuisine> cuisineList = new ArrayList<>();
        for (String c : cuisines)
            cuisineList.add(new Cuisine(c));

        cuisineRepo.saveAll(cuisineList);
        restaurant.addCuisines(cuisineList);
        restRepo.save(restaurant);
    }

    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable long id){
        Optional<Restaurant> toDelete = restRepo.findById(id);
        if (toDelete.isEmpty())
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        dishRepo.deleteAll(toDelete.get().getDishes());
        unlinkRestaurantFromCuisineAndDelete(toDelete.get());
        restRepo.delete(toDelete.get());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * remove the restaurant from its cuisines and delete the cuisine entity
     * if it has no restaurants linked to it
     * @param restaurant: the restaurant to unlink from its cuisines
     */
    private void unlinkRestaurantFromCuisineAndDelete(Restaurant restaurant){
        for (Cuisine cuisine : restaurant.GetCuisineList()){
            cuisine.removeRestaurant(restaurant);
            if (cuisine.GetRestaurants().isEmpty())
                cuisineRepo.delete(cuisine);
        }
    }

    @PostMapping("/ratings")
    public void rateRestaurant(@RequestParam long restaurantId, @RequestParam float rating){
        if (restRepo.findById(restaurantId).isEmpty())
            return;
        Restaurant restaurant = restRepo.findById(restaurantId).get();
        restaurant.addRating(rating);
        restRepo.save(restaurant);
    }

//    @GetMapping("/cuisines")
//    public List<Cuisine> getAllCuisines(){
//        return cuisineRepo.findAll();
//    }
//
//    @DeleteMapping("/cuisines")
//    public void deleteALlCuisines(){
//        cuisineRepo.deleteAll();
//    }

    @PostMapping("/order")
    public void order(@RequestBody Order order){
        long restId = order.getRestaurantId();
        List<Pair<Long, Integer>> orderItems = order.getOrderItems();
        // TODO:(TO FIGURE OUT) what it might do
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
        return restRepo.findById(id).get().getDishes();
    }

}
