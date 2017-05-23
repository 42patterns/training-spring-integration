package com.example.integration.loadbalancer.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;

import java.io.File;

public class FilenameTransformer implements Transformer {

	@Override
	public Message<?> transform(Message<?> message) {
		File file = (File) message.getPayload();
		String absolutePath = file.getAbsolutePath();

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = mapper.createObjectNode();
		json.put("filename", absolutePath);

		// Copying headers so we preserve REPLY QUEUE header
		return MessageBuilder.withPayload(json.toString()).copyHeaders(message.getHeaders()).build();

	}

}
