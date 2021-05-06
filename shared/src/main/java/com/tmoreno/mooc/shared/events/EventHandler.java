package com.tmoreno.mooc.shared.events;

public interface EventHandler<E extends DomainEvent> {
    void handle(E event);
}
