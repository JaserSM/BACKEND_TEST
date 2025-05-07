package com.apiproducts.backendtest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient externalApiWebClient() {
        return WebClient.builder().baseUrl("http://localhost:3001").build();
    }
}

