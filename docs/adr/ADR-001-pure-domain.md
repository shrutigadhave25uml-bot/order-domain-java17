# ADR-001: Keep the domain module pure
- **Status:** Accepted
- **Decision:** `order-domain` uses Java 17 only and has no Spring, JPA, HTTP, or database dependencies.
- **Reason:** Business rules remain independently testable and portable.
- **Consequence:** Framework and infrastructure integration belongs in outer modules.
