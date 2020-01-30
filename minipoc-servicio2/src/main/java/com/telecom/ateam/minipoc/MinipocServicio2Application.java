package com.telecom.ateam.minipoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MinipocServicio2Application {

	public static void main(String[] args) {

		SpringApplication.run(MinipocServicio2Application.class, args);
	}

}
