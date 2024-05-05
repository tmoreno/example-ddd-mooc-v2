package com.tmoreno.mooc.backoffice.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.lifecycle.Startables;

import java.io.File;

public class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static DockerComposeContainer<?> dockerComposeContainer = new DockerComposeContainer<>(new File("docker-compose.yml"))
        .withLocalCompose(true)
        .withExposedService("backoffice-db", 3306);

    static {
        Startables.deepStart(dockerComposeContainer).join();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

    }
}
