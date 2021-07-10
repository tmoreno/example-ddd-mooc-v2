package com.tmoreno.mooc.backoffice.eventpublisher.infrastructure;

import com.tmoreno.mooc.backoffice.eventpublisher.configuration.RabbitMqConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitMqPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String eventName, int version, Map<String, Object> body) {
        String routingKey = String.format("mooc.backoffice.%d.%s", version, eventName);
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE_NAME, routingKey, body);
    }
}
