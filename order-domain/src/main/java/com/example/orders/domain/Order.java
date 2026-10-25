package com.example.orders.domain;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public final class Order {
    private final String id;
    private final List<OrderLine> lines = new ArrayList<>();
    private final List<DomainEvent> events = new ArrayList<>();
    private OrderStatus status = OrderStatus.DRAFT;
    private final Supplier<Instant> clock;

    public Order(String id, Supplier<Instant> clock) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Order id is required");
        this.id = id;
        this.clock = Objects.requireNonNull(clock, "clock");
    }
    public static Order create(Supplier<Instant> clock) { return new Order(UUID.randomUUID().toString(), clock); }
    public String id() { return id; }
    public OrderStatus status() { return status; }
    public List<OrderLine> lines() { return List.copyOf(lines); }
    public List<DomainEvent> drainEvents() { List<DomainEvent> copy = List.copyOf(events); events.clear(); return copy; }
    public BigDecimal total() { return lines.stream().map(OrderLine::total).reduce(BigDecimal.ZERO, BigDecimal::add); }

    public void addLine(OrderLine line) {
        requireStatus(OrderStatus.DRAFT);
        lines.add(Objects.requireNonNull(line, "line"));
    }
    public void confirm() {
        requireStatus(OrderStatus.DRAFT);
        if (lines.isEmpty()) throw new IllegalStateException("An order needs at least one line before confirmation");
        status = OrderStatus.CONFIRMED;
        events.add(new OrderConfirmed(id, clock.get()));
    }
    public void recordPayment() {
        if (status == OrderStatus.CANCELLED) throw new IllegalStateException("A cancelled order cannot be paid");
        requireStatus(OrderStatus.CONFIRMED);
        status = OrderStatus.PAID;
        events.add(new PaymentRecorded(id, clock.get()));
    }
    public void cancel() {
        if (status == OrderStatus.PAID) throw new IllegalStateException("A paid order cannot be cancelled");
        if (status == OrderStatus.CANCELLED) throw new IllegalStateException("Order is already cancelled");
        status = OrderStatus.CANCELLED;
        events.add(new OrderCancelled(id, clock.get()));
    }
    private void requireStatus(OrderStatus expected) {
        if (status != expected) throw new IllegalStateException("Expected " + expected + " but was " + status);
    }
}
