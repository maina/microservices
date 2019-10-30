package com.crotontech.common.configs;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Beans {
    @LoadBalanced //uses ribbon to client balance
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
