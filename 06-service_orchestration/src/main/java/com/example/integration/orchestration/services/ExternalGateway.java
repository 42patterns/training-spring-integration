package com.example.integration.orchestration.services;

import com.example.integration.orchestration.services.accommodation.Accommodation;
import com.example.integration.orchestration.services.events.Event;
import org.springframework.integration.annotation.Gateway;

import java.util.List;

public interface ExternalGateway {

    @Gateway(requestChannel = "artist-events-reqs")
    List<Event> findEvents(String artistName);

    @Gateway(requestChannel = "accommodation-reqs")
    List<Accommodation> findAccommodation(String location);

}
