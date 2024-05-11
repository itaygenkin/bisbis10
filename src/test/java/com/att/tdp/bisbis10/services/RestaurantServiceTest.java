package com.att.tdp.bisbis10.services;

import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Records.RestaurantRecord;
import com.att.tdp.bisbis10.Repository.RestaurantRepo;
import com.att.tdp.bisbis10.Services.RestaurantService;
import com.att.tdp.bisbis10.controller.RestaurantsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = RestaurantsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {


    @MockBean
    private RestaurantService restaurantService;
    private RestaurantRepo restaurantRepo;
    private Restaurant r1;
    private Restaurant r2;
    private RestaurantRecord rr1;

    @BeforeEach
    public void init() {
        restaurantRepo = mock(RestaurantRepo.class);
        restaurantService = new RestaurantService(restaurantRepo);
        r1 = new Restaurant(1L, "Pastory", false, List.of("Italian"));
        r2 = new Restaurant(2L, "Taizu", false, Arrays.asList("Chef", "Asian"));
        rr1 = new RestaurantRecord("Pastory", false, List.of("Italian"));
    }

    @Test
    public void testGetRestaurantById() {
        // Arrange
        long targetId = 1L;
        when(restaurantRepo.findById(1L)).thenReturn(Optional.of(r1));

        // Act
        Optional<Restaurant> response = restaurantService.getRestaurantById(1L);

        // Assert
        assertTrue(response.isPresent());
        assertEquals(1L, response.get().getId());
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
        mockRestaurantList.add(r1);
        mockRestaurantList.add(r2);

        when(restaurantRepo.findAll()).thenReturn(mockRestaurantList);

        // Act
        List<Restaurant> response = restaurantService.getAllRestaurants();

        // Assert
        assertEquals(mockRestaurantList.size(), 2);
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
        assertTrue(mockRestaurantList.isEmpty());
        assertEquals(mockRestaurantList, response);
    }

    @Test
    public void testAddRestaurant() {
        // Arrange
        RestaurantRecord rr = new RestaurantRecord("Shila", false, List.of("Chef"));

        // Act
        restaurantService.addRestaurant(rr1);  // one invocation

        // Assert
        verify(restaurantRepo, times(1)).save(any(Restaurant.class));
    }

    @Test
    public void testUpdateRestaurant() {
        // Arrange
        String updatedName = "Pedro";
        restaurantService.addRestaurant(rr1);  // first invocation
        Mockito.lenient().when(restaurantRepo.findById(1L)).thenReturn(Optional.of(r1));

        // Act
        restaurantService.updateAndSaveRestaurant(r1,  // second invocation
                Optional.of(updatedName),
                Optional.of(true),
                Optional.of(Set.of("Meat")));

        // Assert
        verify(restaurantRepo, times(2)).save(any(Restaurant.class));
    }

}
