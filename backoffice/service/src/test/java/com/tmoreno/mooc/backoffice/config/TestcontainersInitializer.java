package com.tmoreno.mooc.backoffice.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.lifecycle.Startables;

import java.io.File;
import java.time.Duration;

public class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static ComposeContainer dockerComposeContainer = new ComposeContainer(new File("../../docker-compose.yml"))
        .withLocalCompose(true)
        .withExposedService("backoffice-db", 3306, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(120)));

    static {
        Startables.deepStart(dockerComposeContainer).join();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

    }
}
