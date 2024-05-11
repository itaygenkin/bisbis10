package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Repository.RestaurantRepo;
import com.att.tdp.bisbis10.Services.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = RestaurantsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;
    private RestaurantRepo restaurantRepo;

    @BeforeEach
    public void init(){
        restaurantRepo = mock(RestaurantRepo.class);
        restaurantService = new RestaurantService(restaurantRepo);
    }

    @Test
    public void testGetRestaurantById() {
        // Arrange
        List<String> cuisineList = new ArrayList<>();
        cuisineList.add("Italian");
        Restaurant mockRestaurant = new Restaurant("Pastory", false, cuisineList);
        when(restaurantRepo.findById(1L)).thenReturn(Optional.of(mockRestaurant));

        // Act
        Optional<Restaurant> response = restaurantService.getRestaurantById(1L);

        // Assert
        assertTrue(response.isPresent());
        assertEquals(mockRestaurant, response.get());
    }

    @Test
    public void testGetRestaurantById_NotFound() {
        // Arrange
        when(restaurantRepo.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Restaurant> response = restaurantService.getRestaurantById(1L);

        // Assert
        assertTrue(response.isEmpty());
    }

    @Test
    public void testGetAllRestaurants() {
        // Arrange
        List<Restaurant> mockRestaurantList = new ArrayList<>();
        List<String> cuisines = new ArrayList<>();
        cuisines.add("Chef");
        mockRestaurantList.add(new Restaurant("Shila", false, cuisines));
        cuisines.add("Asian");
        mockRestaurantList.add(new Restaurant("Taizu", false, cuisines));

        when(restaurantRepo.findAll()).thenReturn(mockRestaurantList);

        // Act
        List<Restaurant> response = restaurantService.getAllRestaurants();

        // Assert
        assertEquals(mockRestaurantList, response);
    }

    @Test
    public void testGetAllRestaurants_Empty() {
        // Arrange
        List<Restaurant> mockRestaurantList = new ArrayList<>();
        when(restaurantRepo.findAll()).thenReturn(mockRestaurantList);

        // Act
        List<Restaurant> response = restaurantService.getAllRestaurants();

        // Assert
        assertEquals(mockRestaurantList, response);
    }

}
