package com.tmoreno.mooc.shared.events;

public interface MoocEventListener<T extends DomainEvent> {
    void on(T event);
}
