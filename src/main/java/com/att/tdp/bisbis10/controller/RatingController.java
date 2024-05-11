package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    final RestaurantService restaurantService;

    public RatingController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<Void> rateRestaurant(
            @RequestParam(name = "restaurantId") long id,
            @RequestParam(name = "rating") float rating){

        if (rating < 0 || rating > 5)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Restaurant> optionalRestaurant = restaurantService.getRestaurantById(id);
        if (optionalRestaurant.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        restaurantService.rateRestaurant(optionalRestaurant.get(), rating);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
