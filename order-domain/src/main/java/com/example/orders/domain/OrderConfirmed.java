package com.example.orders.domain;
import java.time.Instant;
public record OrderConfirmed(String orderId, Instant occurredAt) implements DomainEvent {}
