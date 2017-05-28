package com.example.integration.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

public class CacheLayer {

	private final Cache cache;

	public CacheLayer(CacheManager cacheManager) {
		this.cache = cacheManager.getCache("default");
	}

	public Object get(Object payload, Map<String, Object> headers) {
        Object key = headers.get("phrase");
        ValueWrapper wrapper = cache.get(key);

		if (wrapper == null) {
			return payload;
		}

		return MessageBuilder
                .withPayload(wrapper.get())
				.setHeader("Cache-Control", "immutable")
				.setHeader("X-Cache", "true")
                .build();
	}

	public void update(Message<?> message) {
        Object key = message.getHeaders().get("phrase");

		cache.put(key, message.getPayload());
	}

}
