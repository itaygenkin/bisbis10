package com.att.tdp.bisbis10.Records;

import java.util.List;

public record RestaurantRecord(
        String name,
        boolean isKosher,
        List<String> cuisines) {
}
