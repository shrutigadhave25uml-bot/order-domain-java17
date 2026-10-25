package com.example.orders.domain;
import java.math.BigDecimal;
import java.util.Objects;
public record OrderLine(String sku, int quantity, BigDecimal unitPrice) {
    public OrderLine {
        if (sku == null || sku.isBlank()) throw new IllegalArgumentException("SKU is required");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        Objects.requireNonNull(unitPrice, "unitPrice");
        if (unitPrice.signum() < 0) throw new IllegalArgumentException("Unit price cannot be negative");
        unitPrice = unitPrice.stripTrailingZeros();
    }
    public BigDecimal total() { return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
}
