package com.telecom.ateam.minipoc;
import com.example.cacheLibrary.configuration.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;



@SpringBootApplication
@Import(RedisConfig.class)
@ComponentScan(basePackages = {"com.example"})
@EnableAutoConfiguration()
public class MinipocServicio2Application extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(MinipocServicio2Application.class, args);
	}

}
