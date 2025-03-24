package com.exercise.brokageFirm.repository;

import com.exercise.brokageFirm.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByUsername(String customerId); //retrieve all assets of a customer

}
