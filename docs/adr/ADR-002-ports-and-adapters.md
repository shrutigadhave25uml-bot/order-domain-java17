# ADR-002: Ports and adapters
- **Status:** Accepted
- **Decision:** Define repository, event publisher, and time-provider interfaces as ports; provide in-memory/system implementations in `order-adapters`.
- **Reason:** Domain and application logic do not depend on infrastructure implementations.
- **Consequence:** Production adapters can later implement the same interfaces.
