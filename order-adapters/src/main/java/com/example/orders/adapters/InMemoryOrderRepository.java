package com.example.orders.adapters;
import com.example.orders.application.OrderRepository;
import com.example.orders.domain.Order;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
public final class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    public void save(Order order) { orders.put(order.id(), order); }
    public Optional<Order> findById(String id) { return Optional.ofNullable(orders.get(id)); }
}
