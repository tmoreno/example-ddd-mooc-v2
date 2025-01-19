package com.tmoreno.mooc.shared.events;

import java.util.List;

public interface EventBus {
    void publish(DomainEvent event);

    void publish(List<DomainEvent> events);
}
