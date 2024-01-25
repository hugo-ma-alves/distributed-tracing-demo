package com.hugomalves.apm.demo.ordersservice.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceMetric {

    private final Counter ordersCounter;

    public OrderServiceMetric(MeterRegistry registry) {
        ordersCounter = Counter.builder("orders.started")
                .description("Counts the number of orders")
                .register(registry);
    }

    public void incrementCounter() {
        ordersCounter.increment();
    }
}