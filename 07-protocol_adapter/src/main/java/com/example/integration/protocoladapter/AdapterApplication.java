package com.example.integration.protocoladapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:async-adapter.xml")
public class AdapterApplication {

    @Bean
    ServerProperties props() {
        ServerProperties properties = new ServerProperties();
        properties.setContextPath("/protocol-adapter");
        return properties;
    }

    public static void main(String[] args) {
        SpringApplication.run(AdapterApplication.class, args);
    }

}
