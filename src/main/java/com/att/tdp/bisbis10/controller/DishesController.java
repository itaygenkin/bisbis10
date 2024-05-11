package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.Models.Dish;
import com.att.tdp.bisbis10.Services.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/restaurants/{id}/dishes")
public class DishesController {

    final DishService dishService;

    public DishesController(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping("")
    public ResponseEntity<Void> addDish(@PathVariable long id,
                                        @RequestParam String name,
                                        @RequestParam String description,
                                        @RequestParam int price){
        if (dishService.addDish(id, name, description, price))
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{dishId}")
    public ResponseEntity<Void> updateDish(@PathVariable long id,
                                           @PathVariable long dishId,
                                           @RequestParam(name = "name") Optional<String> updatedName,
                                           @RequestParam(name = "description") Optional<String> updatedDescription,
                                           @RequestParam(name = "price") Optional<Integer> updatedPrice){

        if (dishService.updateDish(id, dishId, updatedName, updatedDescription, updatedPrice))
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<Void> deleteDish(@PathVariable long id,
                                           @PathVariable long dishId){
        if (dishService.deleteDish(id, dishId))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getDishes(@PathVariable long id){
        List<Dish> dishes = dishService.getDishes(id);
        if (dishes == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }
}
