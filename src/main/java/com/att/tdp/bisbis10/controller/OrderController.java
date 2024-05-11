package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.Models.Order;
import com.att.tdp.bisbis10.Records.OrderRecord;
import com.att.tdp.bisbis10.Services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> order(@RequestBody OrderRecord orderRecord){
        Optional<Order> order = orderService.order(orderRecord);
        return order.map(value -> ResponseEntity
                .status(HttpStatus.OK)
                .body(value)).orElseGet(() -> ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(null));
    }
}
