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

import java.util.*;


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
    public ResponseEntity<Void> addRestaurant(@RequestBody Restaurant restaurant){
        Set<Cuisine> cuisineSet = restaurant.GetCuisineList();
        Set<Cuisine> updatedCuisineSet = new HashSet<>();

        for (Cuisine c : cuisineSet) {
            findMatchCuisine(c.getName()).ifPresentOrElse(updatedCuisineSet::add,
                    () -> updatedCuisineSet.add(c));
        }

        cuisineRepo.saveAll(updatedCuisineSet);
        restaurant.setCuisines(updatedCuisineSet);
        addRestaurantToCuisines(updatedCuisineSet, restaurant);
        restRepo.save(restaurant);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * iterating over a list of cuisines and add @param restaurant to each of them
     * @param cuisines: a list of cuisines to be updated
     * @param restaurant: the restaurant to be added to each cuisine
     */
    private void addRestaurantToCuisines(Iterable<Cuisine> cuisines, Restaurant restaurant){
        for (Cuisine c : cuisines)
            c.addRestaurant(restaurant);
    }

    /**
     * iterating over an array of cuisine names, find the appropriate cuisines
     * or create if absent
     * @param cuisines: an array of String represents cuisine names
     * @return: a List of Cuisines
     */
    private List<Cuisine> findCuisineList(String[] cuisines){
        List<Cuisine> cuisineList = new ArrayList<>();
        for (String c : cuisines)
            findMatchCuisine(c).ifPresentOrElse(cuisineList::add, () ->
                    cuisineList.add(new Cuisine(c))
            );
        return cuisineList;
    }

    /**
     * find a cuisine that match @param cuisineName
     * @param cuisineName: the name of the cuisine to be matched
     * @return: an Optional of Cuisine entity (returns an empty Optional if not found)
     */
    private Optional<Cuisine> findMatchCuisine(String cuisineName) {
        Cuisine exampleCuisine = new Cuisine(cuisineName);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        Example<Cuisine> example = Example.of(exampleCuisine, matcher);
        return cuisineRepo.findOne(example);
    }

    @PutMapping("/restaurants/{id}")
    public void updateRestaurant(@PathVariable long id,
                                 @RequestParam(value= "updatedIsKosher") Optional<Boolean> updatedIsKosher,
                                 @RequestParam(value="name") Optional<String> updatedName,
                                 @RequestParam(value="cuisines") Optional<String[]> cuisines
    ){
        if (restRepo.findById(id).isEmpty())
            return;  // do nothing
        Restaurant restaurant = restRepo.findById(id).get();

        // set name and kosher if they present
        updatedName.ifPresent(restaurant::setName);
        updatedIsKosher.ifPresent(restaurant::setIsKosher);

        // add cuisines if present
        if (cuisines.isPresent()) {
            List<Cuisine> cuisineList = findCuisineList(cuisines.get());
            addRestaurantToCuisines(cuisineList, restaurant);

            cuisineRepo.saveAll(cuisineList);
            restaurant.addCuisines(cuisineList);
        }

        restRepo.save(restaurant);
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

    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable long id){
        Optional<Restaurant> toDelete = restRepo.findById(id);
        if (toDelete.isEmpty())
            return new ResponseEntity<>(HttpStatus.GONE);

        dishRepo.deleteAll(toDelete.get().getDishes());
        unlinkRestaurantFromCuisineAndDelete(toDelete.get());
        toDelete.get().setCuisines(null);
        restRepo.delete(toDelete.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/ratings")
    public void rateRestaurant(@RequestParam long restaurantId, @RequestParam float rating){
        if (restRepo.findById(restaurantId).isEmpty())
            return;
        Restaurant restaurant = restRepo.findById(restaurantId).get();
        restaurant.addRating(rating);
        restRepo.save(restaurant);
    }

    @GetMapping("/cuisines")
    public List<Cuisine> getAllCuisines(){
        return cuisineRepo.findAll();
    }

    @DeleteMapping("/cuisines")
    public void deleteALlCuisines(){
        cuisineRepo.deleteAll();
    }

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
