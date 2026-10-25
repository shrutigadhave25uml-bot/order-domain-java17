package com.example.orders.domain;
import java.time.Instant;
public record OrderCancelled(String orderId, Instant occurredAt) implements DomainEvent {}
