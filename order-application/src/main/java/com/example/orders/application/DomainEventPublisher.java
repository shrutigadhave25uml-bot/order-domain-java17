package com.example.orders.application;
import com.example.orders.domain.DomainEvent;
public interface DomainEventPublisher { void publish(DomainEvent event); }
