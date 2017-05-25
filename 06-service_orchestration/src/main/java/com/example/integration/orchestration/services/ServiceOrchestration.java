package com.example.integration.orchestration.services;

import com.example.integration.orchestration.services.accommodation.Accommodation;
import com.example.integration.orchestration.services.events.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import java.util.List;

public class ServiceOrchestration {

    private static Logger logger = LoggerFactory.getLogger(ServiceOrchestration.class);
    private ExternalGateway gateway;

    @Autowired
    public ServiceOrchestration(ExternalGateway gateway) {
        this.gateway = gateway;
    }

    public String findAccomodationForEvent(Message<?> message) {
        Object payload = message.getPayload();

        //events
        List<Event> events = gateway.findEvents(payload.toString());
        logger.info("Found {} events of {}", events.size(), payload);

        for (Event event : events) {
            try {
                List<Accommodation> acc = gateway.findAccommodation(event.getCity() + ", " + event.getCountry());
            } catch (Exception e) {
                e.printStackTrace();
                // TODO handle exception
            }
        }

        // TODO make sure that we return actual results in place of "Hello World!" string
        return "Hello World!!!";

    }

}
