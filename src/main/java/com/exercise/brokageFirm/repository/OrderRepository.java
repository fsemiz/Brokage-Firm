package com.exercise.brokageFirm.repository;

import com.exercise.brokageFirm.model.Order;
import com.exercise.brokageFirm.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdAndCreateDateBetween(Long customerId, LocalDateTime startDate, LocalDateTime endDate);
    List<Order> findByStatus(OrderStatus status);
}