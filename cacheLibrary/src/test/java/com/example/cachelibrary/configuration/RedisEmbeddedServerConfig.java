package com.example.cachelibrary.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;

import redis.embedded.RedisServer;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
@ConditionalOnProperty(
        value="redisServer",
        havingValue = "embedded")
//@PropertySource(value = "classpath:redisA.properties", encoding = "UTF-8")

public class RedisEmbeddedServerConfig {

    private RedisServer redisServer;

    /** Puerto de Redis. */
    private int redisPort=6370;


    public RedisEmbeddedServerConfig() {

        this.redisServer = new RedisServer(redisPort);
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }

}