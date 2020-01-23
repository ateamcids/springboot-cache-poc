package com.telecom.ateam.minipoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MinipocApplication {

	public static void main(String[] args) {

		SpringApplication.run(MinipocApplication.class, args);
	}

}
