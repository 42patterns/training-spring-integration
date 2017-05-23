package com.example.integration.loadbalancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({
        "classpath:random-config.xml",
        "classpath:sticky-session-config.xml",
        "classpath:round-robin-config.xml",
        "classpath:application-config.xml"
})
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication
                .run(ServerApplication.class, args)
                .getEnvironment().addActiveProfile("random");
    }

    @Bean
    ServerProperties props() {
        ServerProperties properties = new ServerProperties();
        properties.setContextPath("/load-balancer");
        return properties;
    }

}


