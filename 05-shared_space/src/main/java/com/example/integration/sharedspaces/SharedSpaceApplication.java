package com.example.integration.sharedspaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@ImportResource("classpath:shared-space-config.xml")
public class SharedSpaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharedSpaceApplication.class, args);
    }
}
