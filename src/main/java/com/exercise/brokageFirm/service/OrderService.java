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
            if (tryAsset.getUsableSize() < size) {
                throw new RuntimeException("Not enough TRY balance");
            }
            tryAsset.setUsableSize(tryAsset.getUsableSize() - totalCost);
            assetService.updateAsset(tryAsset);
        } else {
            Asset sellingAsset = assetService.findByCustomerIdAndAssetName(customerId, assetName);
            if (sellingAsset.getUsableSize() < size) {
                throw new RuntimeException("Not enough asset balance");
            }
        }

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setAssetName(assetName);
        order.setOrderSide(orderSide); // BUY // SELL
        order.setSize(size);
        order.setPrice(price);
        order.setStatus(OrderStatus.PENDING);
        order.setCreateDate(LocalDateTime.now());

        return orderRepository.save(order);

    }

    public List<Order> getOrders(Long customerId, LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, start, end);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only PENDING orders can be canceled");
        }

        Asset asset = assetService.findByCustomerIdAndAssetName(order.getCustomerId(), order.getOrderSide() == OrderSide.BUY ? "TRY" : order.getAssetName());
        int totalCost = order.getPrice() * order.getSize();
        asset.setUsableSize(asset.getUsableSize() + totalCost);

        assetService.updateAsset(asset);
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    public void matchPendingOrders(Long adminId) {
        List<Order> pendingOrders = orderRepository.findByStatus(OrderStatus.PENDING);

        for (Order order : pendingOrders) {
            int totalCost = order.getSize() * order.getPrice();
            if(order.getOrderSide() == OrderSide.BUY) {
                // TRY bakiyesi kontrolü
                Asset tryAsset = assetService.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY");
                if (tryAsset == null || tryAsset.getUsableSize() < totalCost) {
                    continue; // Yetmiyorsa PENDING bırak
                }

                // TRY bakiyesinden düş
                tryAsset.setSize(tryAsset.getSize() - totalCost);
                assetService.updateAsset(tryAsset);

                // Satın alınan varlığı güncelle
                Asset boughtAsset = assetService.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName());
                if (boughtAsset != null) {
                    boughtAsset.setSize(boughtAsset.getSize() + order.getSize());
                    boughtAsset.setUsableSize(boughtAsset.getUsableSize() + order.getSize());
                    assetService.updateAsset(boughtAsset);
                } else {
                    Asset boughtAsset2 = new Asset();
                    boughtAsset2.setCustomerId(order.getCustomerId());
                    boughtAsset2.setAssetName(order.getAssetName());
                    boughtAsset2.setSize(order.getSize());
                    boughtAsset2.setUsableSize(order.getSize());
                    assetService.updateAsset(boughtAsset2);
                }
            }
            else {
                // TRY bakiyesine ekle
                Asset tryAsset = assetService.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY");
                tryAsset.setSize(tryAsset.getSize() + totalCost);
                tryAsset.setUsableSize(tryAsset.getUsableSize() + totalCost);

                // Satılan varlığı güncelle
                Asset soldAsset = assetService.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName());
                if (soldAsset != null) {
                    soldAsset.setSize(soldAsset.getSize() - order.getSize());
                    soldAsset.setUsableSize(soldAsset.getUsableSize() - order.getSize());
                    assetService.updateAsset(soldAsset);
                }
            }
                // Order'ı MATCHED olarak güncelle
                order.setStatus(OrderStatus.MATCHED);
                orderRepository.save(order);
        }
    }
}
