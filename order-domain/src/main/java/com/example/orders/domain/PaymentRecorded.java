package com.example.orders.domain;
import java.time.Instant;
public record PaymentRecorded(String orderId, Instant occurredAt) implements DomainEvent {}
