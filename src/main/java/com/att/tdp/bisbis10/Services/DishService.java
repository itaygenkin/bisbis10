package com.att.tdp.bisbis10.Services;

import com.att.tdp.bisbis10.Models.Dish;
import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Repository.DishRepo;
import com.att.tdp.bisbis10.Repository.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class DishService {

    final RestaurantRepo restaurantRepo;
    final DishRepo dishRepo;

    @Autowired
    public DishService(RestaurantRepo restaurantRepo, DishRepo dishRepo){
        this.restaurantRepo = restaurantRepo;
        this.dishRepo = dishRepo;
    }

    public boolean addDish(long restaurantId, String name, String description, int price){
        if (restaurantRepo.findById(restaurantId).isEmpty())
            return false;

        Restaurant restaurant = restaurantRepo.findById(restaurantId).get();

        Dish dish = new Dish(name, description, price);
        this.dishRepo.save(dish);
        restaurant.addDish(dish);
        this.restaurantRepo.save(restaurant);

        return true;
    }

    public void saveDish(Dish dish){
        this.dishRepo.save(dish);
    }

    public Optional<Dish> getDishById(long id){
        return this.dishRepo.findById(id);
    }

    public Optional<Restaurant> getResataurantById(long id){
        return this.restaurantRepo.findById(id);
    }

    public boolean deleteDish(long restaurantId, long dishId){
        if (this.restaurantRepo.findById(restaurantId).isEmpty() ||
                this.dishRepo.findById(dishId).isEmpty())
            return false;

        Restaurant restaurant = this.restaurantRepo.findById(restaurantId).get();
        Dish dishToDelete = this.dishRepo.findById(dishId).get();
        restaurant.removeDish(dishToDelete);
        dishRepo.delete(dishToDelete);
        restaurantRepo.save(restaurant);

        return true;
    }

    public boolean updateDish(long restaurantId, long dishId,
                              Optional<String> updatedName,
                              Optional<String> updatedDescription,
                              Optional<Integer> updatedPrice){

        if (this.restaurantRepo.findById(restaurantId).isEmpty() ||
                this.dishRepo.findById(dishId).isEmpty())
            return false;

        Dish updatedDish = this.dishRepo.findById(dishId).get();
        updatedName.ifPresent(updatedDish::setName);
        updatedDescription.ifPresent(updatedDish::setDescription);
        updatedPrice.ifPresent(updatedDish::setPrice);

        this.saveDish(updatedDish);
        return true;
    }

    public Set<Dish> getDishes(long restaurantId){
        Optional<Restaurant> restaurant = this.restaurantRepo.findById(restaurantId);
        return restaurant.map(Restaurant::getDishes).orElse(null);
    }

}
