package com.example.integration.scattergather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({
        "classpath:artist-search-config.xml"
})
public class ServerApplication {

    @Bean
    ServerProperties props() {
        ServerProperties properties = new ServerProperties();
        properties.setContextPath("/scatter-and-gather");
        return properties;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}


