package com.exercise.brokageFirm.service;

import com.exercise.brokageFirm.model.Asset;
import com.exercise.brokageFirm.model.Order;
import com.exercise.brokageFirm.model.OrderSide;
import com.exercise.brokageFirm.model.OrderStatus;
import com.exercise.brokageFirm.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AssetService assetService;

    public Order createOrder(Long customerId, String assetName, OrderSide orderSide, Integer size, Integer price) {
        Asset tryAsset = assetService.findByCustomerIdAndAssetName(customerId, "TRY");


        if (OrderSide.BUY == orderSide) {
            int totalCost = size * price;
            if (tryAsset.getUsableSize() >= totalCost) {
                tryAsset.setUsableSize(tryAsset.getUsableSize() - totalCost);
            } else {
                throw new RuntimeException("Not enough TRY balance");
            }
        } else {
            Asset sellingAsset = assetService.findByCustomerIdAndAssetName(customerId, assetName);
            if (sellingAsset.getUsableSize() < size) {
                throw new RuntimeException("Not enough asset balance");
            }
            sellingAsset.setUsableSize(sellingAsset.getUsableSize() - size);
            assetService.updateAsset(sellingAsset);
        }

        assetService.updateAsset(tryAsset);

        Order order = Order.builder()
                .customerId(customerId)
                .assetName(assetName)
                .orderSide(orderSide)
                .size(size)
                .price(price)
                .status(OrderStatus.PENDING)
                .createDate(LocalDateTime.now())
                .build();

        return orderRepository.save(order);

    }

    public List<Order> getOrders(Long customerId, LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, start, end);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only PENDING orders can be canceled");
        }

        Asset asset = assetService.findByCustomerIdAndAssetName(order.getCustomerId(), order.getOrderSide() == OrderSide.BUY ? "TRY" : order.getAssetName());

        if (order.getOrderSide() == OrderSide.BUY) {
            asset.setUsableSize(asset.getUsableSize() + order.getSize() * order.getPrice());
        } else {
            asset.setUsableSize(asset.getUsableSize() + order.getSize());
        }

        assetService.updateAsset(asset);
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
