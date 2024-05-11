package com.att.tdp.bisbis10.Records;

import java.util.Collection;

public record RestaurantRecord(
        String name,
        boolean isKosher,
        Collection<String> cuisines) {
}
