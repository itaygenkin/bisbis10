package com.att.tdp.bisbis10.Reposiroty;

import com.att.tdp.bisbis10.Models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepo extends JpaRepository<Dish, Long> {
}
