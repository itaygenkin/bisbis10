package com.att.tdp.bisbis10.Reposiroty;

import com.att.tdp.bisbis10.Models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PairRepo extends JpaRepository<OrderItem, Long> {
}
