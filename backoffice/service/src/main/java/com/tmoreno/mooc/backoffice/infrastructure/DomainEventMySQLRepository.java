package com.tmoreno.mooc.backoffice.infrastructure;

import com.tmoreno.mooc.shared.domain.DomainEventRepository;
import com.tmoreno.mooc.shared.events.DomainEvent;
import org.springframework.stereotype.Repository;

@Repository
public class DomainEventMySQLRepository implements DomainEventRepository {

    @Override
    public void store(DomainEvent domainEvent) {

    }
}
