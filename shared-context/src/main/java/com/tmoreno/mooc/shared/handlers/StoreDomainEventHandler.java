package com.tmoreno.mooc.shared.handlers;

import com.tmoreno.mooc.shared.domain.DomainEventRepository;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class StoreDomainEventHandler implements EventHandler<DomainEvent> {

    private final DomainEventRepository repository;

    public StoreDomainEventHandler(DomainEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(DomainEvent event) {
        repository.store(event);
    }
}
