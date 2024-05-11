package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Records.RestaurantRecord;
import com.att.tdp.bisbis10.Services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {

    final RestaurantService restaurantService;

    public RestaurantsController(RestaurantService restService) {
        this.restaurantService = restService;
    }


    @GetMapping
    public ResponseEntity<List<Restaurant>> getRestaurants(){
        return new ResponseEntity<>(restaurantService.getAllRestaurants(), HttpStatus.OK);
    }

    @GetMapping(params = "cuisine")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCuisine(@RequestParam(name = "cuisine") String cuisine){
        return new ResponseEntity<>(
                restaurantService.getRestaurantsByCuisine(cuisine),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable(name = "id") long id){
        Optional<Restaurant> res = restaurantService.getRestaurantById(id);
        return res.map(restaurant -> new ResponseEntity<>(restaurant, HttpStatus.OK)).
                orElseGet(() -> ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(null));
    }

    @PostMapping("")
    public ResponseEntity<Void> addRestaurant(@RequestBody RestaurantRecord restaurant){
        restaurantService.addRestaurant(restaurant);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRestaurant(
            @PathVariable long id,
            @RequestParam(value="name") Optional<String> updatedName,
            @RequestParam(value= "isKosher") Optional<Boolean> updatedIsKosher,
            @RequestParam(value= "cuisines") Optional<Set<String>> updatedCuisines){

        if (restaurantService.getRestaurantById(id).isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Restaurant restaurant = restaurantService.getRestaurantById(id).get();
        restaurantService.updateAndSaveRestaurant(restaurant, updatedName, updatedIsKosher, updatedCuisines);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable long id){
        if (restaurantService.getRestaurantById(id).isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        restaurantService.deleteRestaurantById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
