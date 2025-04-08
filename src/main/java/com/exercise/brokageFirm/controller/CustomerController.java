package com.exercise.brokageFirm.controller;

import com.exercise.brokageFirm.dto.CustomerRegisterRequestDTO;
import com.exercise.brokageFirm.model.Customer;
import com.exercise.brokageFirm.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody @Valid CustomerRegisterRequestDTO request) {
        Customer customer = customerService.registerCustomer(request.getUsername(), request.getPassword(), request.getIsAdmin());
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();  // If empty return noContent
        }
        return ResponseEntity.ok(customers);
    }
}