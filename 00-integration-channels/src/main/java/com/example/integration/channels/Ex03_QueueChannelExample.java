package com.example.integration.channels;

import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;

public class Ex03_QueueChannelExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final QueueChannel queueChannel = new QueueChannel();
		queueChannel.send(MessageBuilder.withPayload("Hello world!!!").build());
		queueChannel.send(MessageBuilder.withPayload("Hello world #2").build());
		queueChannel.send(MessageBuilder.withPayload("Hello world #3").build());

		System.out.println("queueChannel = " + queueChannel.receive(0).getPayload());
	}

}