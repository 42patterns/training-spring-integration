package com.example.integration.scattergather.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class MergeXMLDocumentTransformer extends AbstractTransformer {

	private static final Logger LOGGER = LoggerFactory.getLogger(MergeXMLDocumentTransformer.class);

	@Resource
	private MessageChannel errorChannel;

	@Override
	protected Object doTransform(Message<?> message) throws Exception {

		@SuppressWarnings("unchecked")
		List<String> payload = (List<String>) message.getPayload();

		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document newDocument = documentBuilder.newDocument();
		Element element = newDocument.createElement("root");
		newDocument.appendChild(element);

		for (String xml : payload) {
			try {
				Document document = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
				Element rootElement = document.getDocumentElement();
				element.appendChild(newDocument.adoptNode(rootElement));
			} catch (Exception e) {

				LOGGER.error("failed to parse XML response", e);
				errorChannel.send(MessageBuilder.withPayload(xml).build());

			}
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		transformerFactory.newTransformer().transform(new DOMSource(newDocument), new StreamResult(outputStream));

		String response = outputStream.toString();

		return MessageBuilder.withPayload(response).copyHeaders(message.getHeaders()).build();
	}
}
