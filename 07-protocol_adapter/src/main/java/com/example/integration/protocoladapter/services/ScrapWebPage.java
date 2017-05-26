package com.example.integration.protocoladapter.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;

public class ScrapWebPage extends AbstractTransformer {

    @Override
    protected Object doTransform(Message<?> message) throws Exception {
        String payload = (String) message.getPayload();

        Document doc = Jsoup.parse(payload);
        Element content = doc.getElementById("hidebox");
        return content.text();
    }

}
