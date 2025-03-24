package com.exercise.brokageFirm.controller;

import com.exercise.brokageFirm.service.CustomerService;
import com.exercise.brokageFirm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final OrderService orderService;
    private final CustomerService customerService;

    public AdminController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @PutMapping("/match-orders/{customerId}")
    public ResponseEntity<String> matchOrders(@PathVariable Long customerId) {
        try {
            // Admin yetkisini kontrol et
            if (!customerService.isAdmin(customerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized: Only admins can match orders.");
            }

            // Pending siparişleri işle
            orderService.matchPendingOrders(customerId);
            return ResponseEntity.ok("Orders successfully matched.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error matching orders: " + e.getMessage());
        }
    }
}