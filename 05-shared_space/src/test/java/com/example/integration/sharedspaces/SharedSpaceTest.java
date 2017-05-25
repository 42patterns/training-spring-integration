package com.example.integration.sharedspaces;

import com.example.integration.sharedspaces.services.RandomGeneratorSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SharedSpaceTest {

    @Autowired
    ConnectionFactory jmsConnectionFactory;

    @Test
    public void test() throws Exception {
        final CompletableFuture<String> future = new CompletableFuture();

        // thread which waits for response
        new Thread(() -> {
            JmsTemplate jmsTemplate = new JmsTemplate(jmsConnectionFactory);
            Message message = jmsTemplate.receive("results");

            try {
                future.complete(((TextMessage) message).getText());
            } catch (JMSException e) {
                fail(e.getMessage());
            }
        }).start();

        // sending new request to topic
        JmsTemplate jmsTemplate = new JmsTemplate(jmsConnectionFactory);
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.send("requests",
                session -> session.createTextMessage("Hello"));

        String response = future.get(5, TimeUnit.SECONDS);
        RandomGeneratorSummary value = new ObjectMapper().readerFor(RandomGeneratorSummary.class).readValue(response);

        assertEquals("Hello", value.getKey());
        assertTrue(value.getAverage() >= 50);
    }

}