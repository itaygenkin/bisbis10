package com.att.tdp.bisbis10.Services;

import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Records.RestaurantRecord;
import com.att.tdp.bisbis10.Repository.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RestaurantService {

    final RestaurantRepo restaurantRepo;

    @Autowired
    public RestaurantService(RestaurantRepo restaurantRepo){
        this.restaurantRepo = restaurantRepo;
    }

    public List<Restaurant> getAllRestaurants(){
        return this.restaurantRepo.findAll();
    }

    public Optional<Restaurant> getRestaurantById(long id){
        return this.restaurantRepo.findById(id);
    }

    public void addRestaurant(RestaurantRecord rest){
        Restaurant restaurant = new Restaurant(rest);
        this.restaurantRepo.save(restaurant);
    }

    public void saveRestaurant(Restaurant restaurant){
        this.restaurantRepo.save(restaurant);
    }

    public void updateRestaurant(Restaurant restaurant,
                                 Optional<String> updatedName,
                                 Optional<Boolean> updatedIsKosher,
                                 Optional<Set<String>> updatedCuisineList){
        // updating fields if they present
        updatedIsKosher.ifPresent(System.out::println);
        updatedName.ifPresent(restaurant::setName);
        updatedIsKosher.ifPresent(restaurant::setIsKosher);
        updatedCuisineList.ifPresent(restaurant::setCuisines);
    }

    public void updateAndSaveRestaurant(Restaurant restaurant,
                                        Optional<String> updatedName,
                                        Optional<Boolean> updatedIsKosher,
                                        Optional<Set<String>> updatedCuisineList){

        this.updateRestaurant(restaurant, updatedName, updatedIsKosher, updatedCuisineList);
        this.saveRestaurant(restaurant);
    }

    public void deleteRestaurant(Restaurant restaurant){
        this.restaurantRepo.delete(restaurant);
    }

    public void deleteRestaurantById(long id){
        this.getRestaurantById(id).ifPresent(restaurantRepo::delete);
    }

    public void rateRestaurant(Restaurant restaurant, float rating){
        restaurant.rateRestaurant(rating);
        restaurantRepo.save(restaurant);
    }

    public List<Restaurant> getRestaurantByCuisine(String cuisine){
        List<Restaurant> res = new ArrayList<>();
        for (Restaurant r : restaurantRepo.findAll()){
            if (r.containsCuisine(cuisine))
                res.add(r);
        }
        return res;
    }

}
