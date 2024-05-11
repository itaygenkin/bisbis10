package com.att.tdp.bisbis10.services;

import com.att.tdp.bisbis10.Models.Dish;
import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Repository.DishRepo;
import com.att.tdp.bisbis10.Repository.RestaurantRepo;
import com.att.tdp.bisbis10.Services.DishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DishServiceTest {

    @MockBean
    private DishService dishService;
    private RestaurantRepo restaurantRepo;
    private DishRepo dishRepo;
    private Restaurant r1;
    private Dish d1;
    private Dish d2;

    @BeforeEach
    public void init() {
        restaurantRepo = mock(RestaurantRepo.class);
        dishRepo = mock(DishRepo.class);
        dishService = new DishService(restaurantRepo, dishRepo);
        r1 = new Restaurant(1L, "Taizu", false, List.of("Chef", "Asian"));
        d1 = new Dish("Nams", "Great and asian", 48);
        d2 = new Dish("Fish", "Amnon and Tamar", 72);
    }

    @Test
    public void testAddDish(){
        // Arrange
        when(restaurantRepo.findById(1L)).thenReturn(Optional.of(r1));

        // Act
        boolean isAdded = dishService.addDish(1L, d1.getName(), d1.getDescription(), d1.getPrice());

        // Assert
        assertTrue(isAdded);
        verify(dishRepo, times(1)).save(any(Dish.class));
    }

    @Test
    public void testUpdateDish() {
        // Arrange
        String updatedName = "Beef Fillet";
        String updatedDescription = "Great and Expensive";
        int updatedPrice = 101;

        when(restaurantRepo.findById(1L)).thenReturn(Optional.of(r1));
        when(dishRepo.findById(1L)).thenReturn(Optional.of(d1));

        // Act
        dishService.updateDish(1L, 1L,
                Optional.of(updatedName),
                Optional.of(updatedDescription),
                Optional.of(updatedPrice));

        // Assert
        assertEquals(updatedName, d1.getName());
        assertEquals(updatedDescription, d1.getDescription());
        assertEquals(updatedPrice, d1.getPrice());
    }

}
