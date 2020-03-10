package com.telecom.ateam.minipoc;

import javax.servlet.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@SpringBootApplication
public class MinipocServicio2Application extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(MinipocServicio2Application.class, args);
  }

  @Bean
  public Filter filter() {
    ShallowEtagHeaderFilter filter = new ShallowEtagHeaderFilter();
    return filter;
  }
}
