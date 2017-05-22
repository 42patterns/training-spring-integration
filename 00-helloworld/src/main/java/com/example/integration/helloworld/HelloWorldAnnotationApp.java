package com.example.integration.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class HelloWorldAnnotationApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HelloWorldAnnotationApp.class)
                .web(false)
                .run(args);
    }

    @Bean
    MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    PollableChannel outputChannel() {
        return new QueueChannel(10);
    }

    @Bean
    Service helloService() {
        return new Service();
    }

    @Bean
    @ServiceActivator(inputChannel = "outputChannel", poller = @Poller(fixedDelay = "500"))
    LoggingHandler log() {
        return new LoggingHandler(LoggingHandler.Level.INFO);
    }

    static class Service {

        @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel", requiresReply = "true")
        public String sayHello(String name) {
            return "Hello " + name;
        }

    }

    @Component
    static class Caller implements CommandLineRunner {

        @Autowired
        MessageChannel inputChannel;

        public void run(String... strings) throws Exception {
            for (;;) {
//            System.out.println("outputChannel.receive(0).getPayload() = " + outputChannel.receive(0));
                inputChannel.send(new GenericMessage<>("World"));
                Thread.sleep(500);
            }
        }
    }

}


