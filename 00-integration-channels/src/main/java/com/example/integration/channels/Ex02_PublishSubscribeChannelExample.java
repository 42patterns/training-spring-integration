package com.example.integration.channels;

import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public class Ex02_PublishSubscribeChannelExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PublishSubscribeChannel publishSubscribeChannel = new PublishSubscribeChannel();

		publishSubscribeChannel.subscribe(message -> System.out.println("Subscriber #1: " + message));

		publishSubscribeChannel.subscribe(message -> System.out.println("Subscriber #2: " + message));

		publishSubscribeChannel.send(MessageBuilder.withPayload("Hello world!!!").build());

	}

}
