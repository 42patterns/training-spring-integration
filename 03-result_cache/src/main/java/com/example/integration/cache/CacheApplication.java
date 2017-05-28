package com.example.integration.cache;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.MessageHandlerSpec;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.dsl.support.GenericHandler;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@ImportResource("classpath:cache-config.xml")
@Import(IntegrationFlowConfiguration.class)
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication
                .run(CacheApplication.class, args)
                .getEnvironment().addActiveProfile("java-config");
    }

}

@Profile("java-config")
class IntegrationFlowConfiguration {

    @Bean
    IntegrationFlow flow(CacheLayer cacheLayer) {

        MessageChannel resp = MessageChannels.direct()
                .get();

        return IntegrationFlows.from(Http.inboundGateway("/cache/{phrase}")
                    .replyChannel(resp)
                    .replyTimeout(20000)
                    .payloadExpression("#pathVariables.phrase")
                    .get())
                .enrichHeaders(h -> h.headerExpression("phrase", "payload"))
                .handle(cacheLayer::get)
                .route("headers.containsKey('X-Cache')", m -> m
                        .subFlowMapping("true", sf -> sf.channel(resp))
                        .subFlowMapping("false", sf -> sf.handle(Http.outboundGateway("https://api-rest-profanity.herokuapp.com/profanity/{phrase}")
                                .httpMethod(HttpMethod.GET)
                                .expectedResponseType(String.class)
                                .uriVariable("phrase", "headers.phrase")
                                .get())
                            .wireTap(f -> f.handle(cacheLayer::update))
                            .channel(resp)
                        )
                )
                .get();
    }

    @Bean
    CacheLayer cacheLayer(CacheManager cacheManager) {
        return new CacheLayer(cacheManager);
    }

    @Bean
    CacheManager cacheManager(List<Cache> cacheList) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(cacheList);

        return cacheManager;
    }

    @Bean
    FactoryBean<ConcurrentMapCache> cache() {
        ConcurrentMapCacheFactoryBean mapCacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        mapCacheFactoryBean.setName("default");
        return mapCacheFactoryBean;
    }

}