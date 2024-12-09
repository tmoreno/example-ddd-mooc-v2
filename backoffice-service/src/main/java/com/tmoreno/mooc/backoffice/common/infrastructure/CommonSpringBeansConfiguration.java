package com.tmoreno.mooc.backoffice.common.infrastructure;

import com.tmoreno.mooc.shared.domain.DomainEventRepository;
import com.tmoreno.mooc.shared.handlers.StoreDomainEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonSpringBeansConfiguration {

    @Bean
    public StoreDomainEventHandler storeDomainEventHandler(DomainEventRepository repository) {
        return new StoreDomainEventHandler(repository);
    }
}
