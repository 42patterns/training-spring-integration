package com.example.integration.helloworld;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

public class HelloWorldXmlApp {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("hello-world-integration.xml");
        MessageChannel inputChannel = context.getBean("inputChannel", MessageChannel.class);

        for (;;) {
//            System.out.println("outputChannel.receive(0).getPayload() = " + outputChannel.receive(0));
            inputChannel.send(new GenericMessage<>("World"));
            Thread.sleep(500);
        }
    }

    static class Service {

        public String sayHello(String name) {
            return "Hello " + name;
        }

    }

}

