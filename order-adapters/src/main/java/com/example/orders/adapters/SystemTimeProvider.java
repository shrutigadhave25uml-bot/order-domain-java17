package com.example.orders.adapters;
import com.example.orders.application.TimeProvider;
import java.time.Instant;
public final class SystemTimeProvider implements TimeProvider { public Instant now() { return Instant.now(); } }
