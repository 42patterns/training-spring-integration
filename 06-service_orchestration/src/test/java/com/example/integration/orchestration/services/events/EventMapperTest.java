package com.example.integration.orchestration.services.events;

import org.junit.Test;
import org.springframework.messaging.support.GenericMessage;

public class EventMapperTest {

    @Test
    public void should_extract_json_data() throws Exception {
        EventMapper m = new EventMapper();
        Object o = m.doTransform(new GenericMessage<>(jsonEvent));

        System.out.println("event = " + o);
    }

    final private String jsonEvent = "[" +
            "  {\n" +
            "    \"id\": \"14414058\",\n" +
            "    \"artist_id\": \"26633\",\n" +
            "    \"url\": \"http://www.bandsintown.com/event/14414058?app_id=music-integration&artist=Tool&came_from=67\",\n" +
            "    \"datetime\": \"2017-05-25T19:30:00\",\n" +
            "    \"venue\": {\n" +
            "      \"name\": \"Giant Center\",\n" +
            "      \"latitude\": \"40.285717\",\n" +
            "      \"longitude\": \"-76.6758748\",\n" +
            "      \"city\": \"Hershey\",\n" +
            "      \"region\": \"PA\",\n" +
            "      \"country\": \"United States\"\n" +
            "    },\n" +
            "    \"offers\": [\n" +
            "      {\n" +
            "        \"type\": \"Tickets\",\n" +
            "        \"url\": \"http://www.bandsintown.com/event/14414058/buy_tickets?app_id=music-integration&artist=Tool&came_from=67\",\n" +
            "        \"status\": \"available\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"lineup\": [\n" +
            "      \"Tool\"\n" +
            "    ]\n" +
            "  }" +
            "]";

}