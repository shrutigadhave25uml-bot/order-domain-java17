package com.example.orders.domain;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Order draft() { return new Order("order-1", () -> Instant.parse("2026-01-01T00:00:00Z")); }
    @Test void cannotConfirmEmptyOrder() { assertThrows(IllegalStateException.class, () -> draft().confirm()); }
    @Test void quantityMustBePositive() { assertThrows(IllegalArgumentException.class, () -> new OrderLine("SKU", 0, new BigDecimal("2.00"))); }
    @Test void totalIsDerivedFromLinePriceAndQuantity() {
        Order o = draft(); o.addLine(new OrderLine("A", 2, new BigDecimal("10.50")));
        o.addLine(new OrderLine("B", 1, new BigDecimal("3.00")));
        assertEquals(0, new BigDecimal("24.00").compareTo(o.total()));
    }
    @Test void validLifecycleEmitsEvents() {
        Order o = draft(); o.addLine(new OrderLine("A", 1, new BigDecimal("5.00")));
        o.confirm(); o.recordPayment();
        assertEquals(OrderStatus.PAID, o.status());
        assertEquals(2, o.drainEvents().size());
    }
    @Test void cancelledOrderCannotBePaid() {
        Order o = draft(); o.addLine(new OrderLine("A", 1, new BigDecimal("5.00")));
        o.confirm(); o.cancel();
        assertThrows(IllegalStateException.class, o::recordPayment);
    }
    @Test void paidOrderCannotReturnToDraftOrBeCancelled() {
        Order o = draft(); o.addLine(new OrderLine("A", 1, new BigDecimal("5.00")));
        o.confirm(); o.recordPayment();
        assertThrows(IllegalStateException.class, () -> o.addLine(new OrderLine("B", 1, BigDecimal.ONE)));
        assertThrows(IllegalStateException.class, o::cancel);
    }
    @Test void cancelledOrderCannotBeConfirmedAgain() {
        Order o = draft(); o.addLine(new OrderLine("A", 1, new BigDecimal("5.00"))); o.cancel();
        assertThrows(IllegalStateException.class, o::confirm);
    }
}
