package com.tmoreno.mooc.shared.domain;

import com.tmoreno.mooc.shared.events.DomainEvent;

public interface DomainEventRepository {
    void store(DomainEvent event);
}
