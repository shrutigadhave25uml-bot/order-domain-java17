package com.example.orders.application;
import com.example.orders.domain.Order;
import java.util.Optional;
public interface OrderRepository {
    void save(Order order);
    Optional<Order> findById(String id);
}
