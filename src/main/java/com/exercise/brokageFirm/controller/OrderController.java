package com.exercise.brokageFirm.controller;

import com.exercise.brokageFirm.dto.OrderCreateRequestDTO;
import com.exercise.brokageFirm.model.Order;
import com.exercise.brokageFirm.model.OrderSide;
import com.exercise.brokageFirm.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderCreateRequestDTO request) {
        Order createdOrder = orderService.createOrder(
                request.getCustomerId(),
                request.getAssetName(),
                OrderSide.valueOf(request.getSide().toUpperCase()),
                request.getSize(),
                request.getPrice()
        );
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(
            @RequestParam Long customerId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {

        return ResponseEntity.ok(orderService.getOrders(customerId, startDate, endDate));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
