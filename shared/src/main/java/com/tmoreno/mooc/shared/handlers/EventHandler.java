package com.tmoreno.mooc.shared.handlers;

import com.tmoreno.mooc.shared.events.DomainEvent;

public interface EventHandler<E extends DomainEvent> {
    void handle(E event);
}
