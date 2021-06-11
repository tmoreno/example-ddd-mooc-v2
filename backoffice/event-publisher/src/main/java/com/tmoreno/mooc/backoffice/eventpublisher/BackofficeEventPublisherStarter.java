package com.tmoreno.mooc.backoffice.eventpublisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BackofficeEventPublisherStarter {
    public static void main(String[] args) {
        SpringApplication.run(BackofficeEventPublisherStarter.class);
    }
}
