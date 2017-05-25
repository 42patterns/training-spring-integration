package com.example.integration.orchestration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:orchestration-config.xml")
public class ServiceOrchestrationApplication {

    @Bean
    ServerProperties props() {
        ServerProperties properties = new ServerProperties();
        properties.setContextPath("/service-orchestration");
        return properties;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceOrchestrationApplication.class, args);
    }

}
