package com.telecom.ateam.minipoc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.example.cacheLibrary", "com.example.cacheLibrary.configuration.RedisConfig"})
public class Config {
}
