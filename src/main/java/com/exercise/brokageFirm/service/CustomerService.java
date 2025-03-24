package com.exercise.brokageFirm.service;

import com.exercise.brokageFirm.model.Customer;
import com.exercise.brokageFirm.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    public Customer registerCustomer(String username, String password, boolean isAdmin) {
        passwordEncoder = new BCryptPasswordEncoder();
        Customer customer = Customer.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .isAdmin(isAdmin)
                .build();
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
