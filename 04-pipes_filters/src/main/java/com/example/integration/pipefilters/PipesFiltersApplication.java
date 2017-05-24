package com.example.integration.pipefilters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

@EnableJms
@SpringBootApplication
@ImportResource("classpath:pipes-and-filters-config.xml")
public class PipesFiltersApplication {

    public static void main(String[] args) {
        SpringApplication.run(PipesFiltersApplication.class, args);
    }

//    @Autowired
//    ApplicationContext ctx;
//
//    @PostConstruct
//    public void list() {
//        Map<String, Object> beansOfType = ctx.getBeansOfType(Object.class);
//        beansOfType.entrySet().forEach(System.out::println);
//    }


}
