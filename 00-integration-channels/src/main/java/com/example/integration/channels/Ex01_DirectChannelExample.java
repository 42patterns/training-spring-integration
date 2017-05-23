package com.example.integration.channels;

import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public class Ex01_DirectChannelExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DirectChannel directChannel = new DirectChannel();

		directChannel.subscribe(message -> System.out.println("Subscriber #1: " + message));
		directChannel.subscribe(message -> System.out.println("Subscriber #1: " + message));

		directChannel.send(MessageBuilder.withPayload("Hello world!!!").build());

	}

}