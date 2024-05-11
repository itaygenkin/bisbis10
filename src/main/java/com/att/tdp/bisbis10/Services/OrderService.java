package com.att.tdp.bisbis10.Services;

import com.att.tdp.bisbis10.Models.Order;
import com.att.tdp.bisbis10.Models.OrderItem;
import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Records.OrderRecord;
import com.att.tdp.bisbis10.Repository.DishRepo;
import com.att.tdp.bisbis10.Repository.OrderRepo;
import com.att.tdp.bisbis10.Repository.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    final RestaurantRepo restaurantRepo;
    final OrderRepo orderRepo;
    final DishRepo dishRepo;

    @Autowired
    public OrderService(RestaurantRepo restaurantRepo, OrderRepo orderRepo, DishRepo dishRepo){
        this.restaurantRepo = restaurantRepo;
        this.orderRepo = orderRepo;
        this.dishRepo = dishRepo;
    }

    public Optional<Order> order(OrderRecord orderRecord){
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(orderRecord.restaurantId());
        if (optionalRestaurant.isEmpty())
            return Optional.empty();
        Restaurant restaurant = optionalRestaurant.get();

        // checking if the dishes of the order exists
        for (OrderItem oi : orderRecord.orderItems()){
            if (!restaurant.containsDish(oi.dishId))
                return Optional.empty();
        }

        Order order = new Order(orderRecord);
        orderRepo.save(order);
        return Optional.of(order);
    }


}
