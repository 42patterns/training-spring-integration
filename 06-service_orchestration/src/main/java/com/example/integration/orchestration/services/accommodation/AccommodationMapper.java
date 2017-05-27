package com.example.integration.orchestration.services.accommodation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;

import java.util.ArrayList;
import java.util.List;

public class AccommodationMapper extends AbstractTransformer {

    @Override
    protected Object doTransform(Message<?> message) throws Exception {
        Object payload = message.getPayload();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = mapper.readValue(payload.toString(), ObjectNode.class);
        List<Accommodation> places = new ArrayList<>();
        jsonNode.withArray("results").forEach(node -> places.add(deserialize(node)));
        return places;
    }

    public Accommodation deserialize(JsonNode node) {
        Accommodation acc = new Accommodation();
        acc.setName(node.get("name").asText());
        acc.setAddress(node.get("formatted_address").asText());
        if (node.withArray("photos").size() > 0) {
            String photo = node.withArray("photos").get(0).get("photo_reference").asText();
            acc.setThumbURL(String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=600&photoreference=%s&key={api-key}", photo));
        }
        return acc;
    }

}
