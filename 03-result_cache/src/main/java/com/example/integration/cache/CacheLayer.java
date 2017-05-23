package com.example.integration.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public class CacheLayer {

	private final Cache cache;
	private final MessageChannel messageChannel;

	public CacheLayer(CacheManager cacheManager, MessageChannel messageChannel) {
		this.cache = cacheManager.getCache("default");
		this.messageChannel = messageChannel;
	}

	public Message<?> getOrRequest(Message<?> message) {
        Object key = message.getHeaders().get("phrase");
        ValueWrapper wrapper = cache.get(key);

		if (wrapper == null) {
			messageChannel.send(message);
			return null;
		}

		return MessageBuilder
                .withPayload(wrapper.get())
                .setHeader("Cache-Control", "immutable")
                .build();
	}

	public void update(Message<?> message) {
        Object key = message.getHeaders().get("phrase");

		cache.put(key, message.getPayload());
	}

}
