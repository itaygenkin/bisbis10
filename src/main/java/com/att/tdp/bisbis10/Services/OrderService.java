package com.att.tdp.bisbis10.Services;

import com.att.tdp.bisbis10.Models.Order;
import com.att.tdp.bisbis10.Models.Restaurant;
import com.att.tdp.bisbis10.Records.OrderRecord;
import com.att.tdp.bisbis10.Repository.OrderRepo;
import com.att.tdp.bisbis10.Repository.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    final RestaurantRepo restaurantRepo;
    final OrderRepo orderRepo;

    @Autowired
    public OrderService(RestaurantRepo restaurantRepo, OrderRepo orderRepo){
        this.restaurantRepo = restaurantRepo;
        this.orderRepo = orderRepo;
    }

    public Order order(OrderRecord orderRecord){
        Optional<Restaurant> restaurant = restaurantRepo.findById(orderRecord.restaurantId());
        if (restaurant.isEmpty())
            return null;
        Order order = new Order(orderRecord);
        orderRepo.save(order);
        return order;
    }


}
