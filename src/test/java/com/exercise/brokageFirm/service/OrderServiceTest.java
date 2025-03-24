package com.exercise.brokageFirm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.exercise.brokageFirm.model.*;
import com.exercise.brokageFirm.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AssetService assetService; // Mock ekledik

    @InjectMocks
    private OrderService orderService;

    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        sampleOrder = new Order();
        sampleOrder.setId(1L);
        sampleOrder.setCustomerId(1L);
        sampleOrder.setAssetName("BTC");
        sampleOrder.setOrderSide(OrderSide.BUY);
        sampleOrder.setStatus(OrderStatus.PENDING);
        sampleOrder.setSize(100);
        sampleOrder.setPrice(10);
    }

    @Test
    void testCreateOrder_Success() {
        // Arrange
        Long customerId = 1L;
        String assetName = "TRY";
        int orderSize = 1;
        OrderSide orderSide = OrderSide.BUY;

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setUsername("testuser");

        Asset asset = new Asset();
        asset.setCustomerId(customerId);
        asset.setAssetName(assetName);
        asset.setUsableSize(50000); // Yeterli bakiye var

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setAssetName("BTC");
        order.setSize(orderSize);
        order.setOrderSide(orderSide);
        order.setStatus(OrderStatus.PENDING);
        order.setPrice(1000);

        // Mock
        when(assetService.findByCustomerIdAndAssetName(customerId, assetName)).thenReturn(asset);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order createdOrder = orderService.createOrder(order.getCustomerId(),order.getAssetName(),order.getOrderSide(),order.getSize(),order.getPrice());

        // Assert
        assertNotNull(createdOrder);
        assertEquals(OrderStatus.PENDING, createdOrder.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testCreateOrder_InsufficientTRYBalance() {
        Customer customer = new Customer(1L, "user", "pass", false);
        Asset tryAsset = new Asset(1L, customer.getId(), "TRY", 50, 0); // TRY bakiyesi yetersiz

        // Mock
        when(assetService.findByCustomerIdAndAssetName(1L, "TRY")).thenReturn(tryAsset);

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(1L, "BTC", OrderSide.BUY, 50000, 100);
        });

        // Assert
        assertEquals("Not enough TRY balance", exception.getMessage());
    }

    @Test
    void testCancelOrder_Success() {
        Asset asset = new Asset();
        asset.setCustomerId(1L);
        asset.setAssetName("TRY");
        asset.setSize(10);
        asset.setUsableSize(5000);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));
        when(assetService.findByCustomerIdAndAssetName(1L, "TRY")).thenReturn(asset);

        // Act
        orderService.cancelOrder(1L);

        // Assert
        assertEquals(OrderStatus.CANCELED, sampleOrder.getStatus());
        assertEquals(6000, asset.getUsableSize()); // 2 birim iade edildi
        verify(orderRepository, times(1)).save(sampleOrder);
    }

    /*@Test
    void testMatchedOrderStatus() {
        Order order = new Order(1L, null, "BTC", 1, 50000, "BUY", OrderStatus.PENDING);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        orderService.matchOrder(1L);

        assertEquals(OrderStatus.MATCHED, order.getStatus());
        verify(orderRepository, times(1)).save(order);
    }*/
}