package com.exercise.brokageFirm.service;

import com.exercise.brokageFirm.model.Customer;
import com.exercise.brokageFirm.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        // Test öncesi setup
        customer = new Customer(1L, "testUser", "encodedPassword", false);
    }

    @Test
    void testRegisterCustomer_Success() {
        // Given
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // When
        Customer registeredCustomer = customerService.registerCustomer("testUser", "password123", false);

        // Then
        assertNotNull(registeredCustomer);
        assertEquals("testUser", registeredCustomer.getUsername());
        assertEquals("encodedPassword", registeredCustomer.getPassword());
        verify(customerRepository, times(1)).save(any(Customer.class)); // save metodunun bir kez çağrıldığını doğrula
    }

    @Test
    void testGetCustomerByUsername_Success() {
        // Given
        when(customerRepository.findByUsername("testUser")).thenReturn(java.util.Optional.of(customer));

        // When
        Customer foundCustomer = customerService.getCustomerByUsername("testUser").orElse(null);

        // Then
        assertNotNull(foundCustomer);
        assertEquals("testUser", foundCustomer.getUsername());
        assertEquals("encodedPassword", foundCustomer.getPassword());
        verify(customerRepository, times(1)).findByUsername("testUser"); // findByUsername metodunun bir kez çağrıldığını doğrula
    }

    @Test
    void testGetCustomerByUsername_NotFound() {
        // Given
        when(customerRepository.findByUsername("nonExistentUser")).thenReturn(java.util.Optional.empty());

        // When
        Customer foundCustomer = customerService.getCustomerByUsername("nonExistentUser").orElse(null);

        // Then
        assertNull(foundCustomer);
        verify(customerRepository, times(1)).findByUsername("nonExistentUser");
    }
}