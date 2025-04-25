package com.situationpuzzle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        // You might want to customize the RestTemplate here
        // e.g., adding interceptors, message converters, etc.
        return new RestTemplate();
    }

}