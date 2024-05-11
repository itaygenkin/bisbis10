package com.att.tdp.bisbis10.Repository;

import com.att.tdp.bisbis10.Models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepo extends JpaRepository<Dish, Long> {
}
