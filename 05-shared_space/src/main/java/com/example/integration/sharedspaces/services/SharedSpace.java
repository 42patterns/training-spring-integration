package com.example.integration.sharedspaces.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SharedSpace {

    private static Logger logger = LoggerFactory.getLogger(SharedSpace.class);
    private final Long threshold;
    private MultiValueMap<String, Integer> map = new LinkedMultiValueMap<>();
    private List<String> processed = new ArrayList<>();

    public SharedSpace() {
        this.threshold = 50l;
    }

    public SharedSpace(Long threshold) {
        this.threshold = threshold;
    }

    public Object handleMessage(RandomGeneratorResult result) {

        if (processed.contains(result.getKey())) {
            return null;
        }

        map.add(result.getKey(), result.getValue());

        Double average = map.get(result.getKey())
                .stream()
                .collect(Collectors.averagingInt(Integer::valueOf));

        logger.info("Avg {} of {}", average.longValue(), map.get(result.getKey()));
        if (average < threshold) {
            return MessageBuilder.withPayload(result.getKey()).build();
        } else {
            logger.info("Target reached");

            map.remove(result.getKey());
            processed.add(result.getKey());
            return new RandomGeneratorSummary(result.getKey(),
                    map.get(result.getKey()),
                    average.longValue());
        }
    }
}
