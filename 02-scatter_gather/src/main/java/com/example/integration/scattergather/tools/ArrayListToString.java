package com.example.integration.scattergather.tools;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;

public class ArrayListToString implements Transformer {

	@Override
	public Message<?> transform(Message<?> message) {
		System.out.println(message);
		return MessageBuilder.withPayload("dupa").copyHeaders(message.getHeaders()).build();
	}

}
