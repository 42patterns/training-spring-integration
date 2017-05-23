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
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.List;

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
    IntegrationFlow preCache(MessageChannel httpResp,
                             CacheLayer cacheLayer) {
        return IntegrationFlows.from(Http.inboundGateway("/cache/{phrase}")
                .replyChannel(httpResp)
                .replyTimeout(20000)
                .payloadExpression("#pathVariables.phrase")
                .get())
                .enrichHeaders(h -> h.headerExpression("phrase", "payload"))
                .handle(cacheLayer, "getOrRequest")
                .get();
    }

    @Bean
    IntegrationFlow caching(MessageChannel cacheUpdate, CacheLayer cacheLayer) {
        return IntegrationFlows.from(cacheUpdate)
                .handle(msg -> cacheLayer.update(msg))
                .get();
    }

    @Bean
    IntegrationFlow cacheDownload(MessageChannel httpRequest, MessageChannel httpResp) {
        return IntegrationFlows.from(httpRequest)
                .handle(Http
                        .outboundGateway("https://api-rest-profanity.herokuapp.com/profanity/{phrase}")
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(String.class)
                        .uriVariable("phrase", "headers.phrase")
                        .get())
                .channel(httpResp)
                .get();
    }

    @Bean
    MessageChannel httpRequest() {
        return MessageChannels.direct().get();
    }

    @Bean
    MessageChannel cacheUpdate() {
        return MessageChannels.queue().get();
    }

    @Bean
    MessageChannel httpResp(MessageChannel cacheUpdate) {
        return MessageChannels.queue()
                .interceptor(new WireTap(cacheUpdate))
                .get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(10));
        return pollerMetadata;
    }

    @Bean
    CacheLayer cacheLayer(CacheManager cacheManager, MessageChannel httpRequest) {
        return new CacheLayer(cacheManager, httpRequest);
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