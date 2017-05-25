package com.example.integration.orchestration.services.accommodation;

import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;

public class AccommodationMapper extends AbstractTransformer {

    @Override
    protected Object doTransform(Message<?> message) throws Exception {
        return "Hello world (after transformation)!";
    }
}
