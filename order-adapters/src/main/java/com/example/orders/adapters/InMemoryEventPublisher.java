package com.example.orders.adapters;
import com.example.orders.application.DomainEventPublisher;
import com.example.orders.domain.DomainEvent;
import java.util.ArrayList;
import java.util.List;
public final class InMemoryEventPublisher implements DomainEventPublisher {
    private final List<DomainEvent> published = new ArrayList<>();
    public void publish(DomainEvent event) { published.add(event); }
    public List<DomainEvent> publishedEvents() { return List.copyOf(published); }
}
