package com.att.tdp.bisbis10.Reposiroty;

import com.att.tdp.bisbis10.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {
}
