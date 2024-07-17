package com.example.CoffeeShop.repository;

import com.example.CoffeeShop.entity.event.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<OrderEvent, UUID> {
}
