package com.example.integration.channels;

import org.springframework.integration.channel.RendezvousChannel;
import org.springframework.integration.support.MessageBuilder;

public class Ex04_RendezvousChannelExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final RendezvousChannel rendezvousChannel = new RendezvousChannel();

		new Thread(() -> System.out.println(rendezvousChannel.receive())).start();

		rendezvousChannel.send(MessageBuilder.withPayload("Hello world!!!").build());

	}

}