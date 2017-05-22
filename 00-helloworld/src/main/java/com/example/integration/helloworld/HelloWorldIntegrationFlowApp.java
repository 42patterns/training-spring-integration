package com.example.integration.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class HelloWorldIntegrationFlowApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HelloWorldIntegrationFlowApp.class)
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
    LoggingHandler log() {
        return new LoggingHandler(LoggingHandler.Level.INFO);
    }

    @Bean
    IntegrationFlow flow(MessageChannel inputChannel, PollableChannel outputChannel) {
        return IntegrationFlows.from(inputChannel)
                .handle(helloService())
                .channel(outputChannel)
                .handle(log(), s -> s.poller(Pollers.fixedDelay(1000)))
                .get();
    }

    static class Service {

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

