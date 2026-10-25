# Order Domain — Java 17

A small multi-module Maven example implementing the uploaded order requirements.

## Modules
- `order-domain`: pure business model, immutable order lines, state transitions, domain events.
- `order-application`: ports for persistence, event publication, and time.
- `order-adapters`: in-memory repository/event publisher and system clock adapter, plus JUnit tests.
- `docs/adr`: architecture decision records.

## Requirements covered
- States: `DRAFT`, `CONFIRMED`, `PAID`, `CANCELLED`.
- Cannot confirm an empty order.
- Quantity must be positive; line prices are immutable `BigDecimal` values.
- Cancelled orders cannot be paid.
- Paid orders cannot be changed back to draft or cancelled.
- Total is derived from immutable line prices and quantities.
- Emits `OrderConfirmed`, `PaymentRecorded`, and `OrderCancelled`.

## Run tests
Install Java 17 and Maven, then run from the repository root:
```bash
mvn test
```

## Open in VS Code
1. Extract the ZIP.
2. Open the `order-domain-java17` folder in VS Code.
3. Ensure Java 17 and Maven are installed.
4. Run `mvn test` in the integrated terminal.

## Notes
This is a starter implementation based on the supplied brief. The brief does not specify currency, discounts, refunds, persistence format, or event delivery guarantees, so those are intentionally not invented.
