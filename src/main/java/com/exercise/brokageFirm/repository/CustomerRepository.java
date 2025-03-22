package com.exercise.brokageFirm.repository;

import com.exercise.brokageFirm.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(Long customerId); //retrieve all assets of a customer

}
