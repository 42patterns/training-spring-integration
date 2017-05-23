package com.example.integration.channels;

import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Ex05_ExecutorChannelExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Running on thread: " + Thread.currentThread().getName());

		final ExecutorChannel executorChannel = new ExecutorChannel(new ThreadPoolExecutor(4, 8, 1000, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<>(100)));

		executorChannel.subscribe(message -> System.out.println(Thread.currentThread().getName() + ": " + message));

		executorChannel.send(MessageBuilder.withPayload("Hello world!!!").build());

	}

}