package com.example.integration.pipefilters;

import com.example.integration.pipefilters.model.SearchQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.integration.transformer.Transformer;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.messaging.Message;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

@EnableJms
@SpringBootApplication
@ImportResource("classpath:pipes-and-filters-config.xml")
public class PipesFiltersApplication {

    @Bean
    public Transformer categoryExtract() {
        return new AbstractTransformer() {
            @Override
            protected Object doTransform(Message<?> message) throws Exception {
                SearchQueryRequest query = (SearchQueryRequest) message.getPayload();

                if (query.getCategory().contains("/")) {
                    query.setSubcategory(query.getCategory().split("/")[1]);
                }
                return query;
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(PipesFiltersApplication.class, args);
    }

}
