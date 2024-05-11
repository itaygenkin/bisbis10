package com.att.tdp.bisbis10.Records;

import com.att.tdp.bisbis10.Models.OrderItem;

import java.util.List;

public record OrderRecord(
        long restaurantId,
        List<OrderItem> orderItems) {
}
