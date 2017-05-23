package com.example.integration.loadbalancer.tools;

import org.springframework.integration.dispatcher.LoadBalancingStrategy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class RandomLoadBalancer implements LoadBalancingStrategy {

	@Override
	public Iterator<MessageHandler> getHandlerIterator(Message<?> message, Collection<MessageHandler> handlers) {
		ArrayList<MessageHandler> list = new ArrayList<>(handlers);
		Collections.shuffle(list);
		return list.iterator();
	}

}
