package com.example.integration.channels;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.support.channel.BeanFactoryChannelResolver;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.DestinationResolver;

import java.io.File;

public class Ex06_FileCopyQueueExample {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("file-copy-queue-config.xml");

		DestinationResolver<MessageChannel> channelResolver = new BeanFactoryChannelResolver(applicationContext);
		MessageChannel messageChannel = channelResolver.resolveDestination("hello-world-channel");

		messageChannel.send(MessageBuilder.withPayload("Hello world!").build());
	}

}
