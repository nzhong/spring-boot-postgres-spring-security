package com.learn.springboot.server;

import java.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@ComponentScan("com.learn.springboot")
@SpringBootApplication
public class AppServer {
  protected static Properties appProperties;

  public static void main(String[] args) {
    appProperties = new AppServerCliProperties(args).parseAndRead();
    SpringApplication.run(AppServer.class, args);
  }

  @Bean
  public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    final PropertySourcesPlaceholderConfigurer PROPERTY_CONFIG = new PropertySourcesPlaceholderConfigurer();
    PROPERTY_CONFIG.setProperties(AppServer.appProperties);
    return PROPERTY_CONFIG;
  }
}
