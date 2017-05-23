package com.example.integration.loadbalancer.tools;

import org.springframework.integration.dispatcher.LoadBalancingStrategy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StickySessionLoadBalancer implements LoadBalancingStrategy {

	private final ConcurrentHashMap<UUID, MessageHandler> sessions = new ConcurrentHashMap<>();

	@Override
	public Iterator<MessageHandler> getHandlerIterator(Message<?> message, Collection<MessageHandler> handlers) {
		ArrayList<MessageHandler> list = new ArrayList<>(handlers);

		sessions.putIfAbsent(message.getHeaders().getId(), list.stream().findFirst().get());
		MessageHandler handler = sessions.get(message.getHeaders().getId());
		return Arrays.asList(handler).iterator();
	}

}
