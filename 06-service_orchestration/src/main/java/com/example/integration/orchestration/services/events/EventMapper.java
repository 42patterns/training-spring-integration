package com.example.integration.orchestration.services.events;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.util.List;

public class EventMapper extends AbstractTransformer {

	@Override
	protected Object doTransform(Message<?> message) throws Exception {
		Object payload = message.getPayload();

		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Event.class, new EventDeserializer());
		mapper.registerModule(module);

		List<Event> events = mapper.readValue(payload.toString(), new TypeReference<List<Event>>(){});
		return events;

	}

	public class EventDeserializer extends StdDeserializer<Event> {

		public EventDeserializer() {
			super(Event.class);
		}

		@Override
		public Event deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonNode node = p.getCodec().readTree(p);

			Event event = new Event();
			event.setVenueName(node.get("venue").get("name").asText());
			event.setCity(node.get("venue").get("city").asText());
			event.setCountry(node.get("venue").get("country").asText());
			event.setWebsite(node.get("url").asText());
			event.setStartDate(node.get("datetime").asText());
			return event;
		}
	}

}
