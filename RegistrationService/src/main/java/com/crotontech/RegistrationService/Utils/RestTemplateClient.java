package com.crotontech.RegistrationService.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateClient {

    @Autowired
    RestTemplate restTemplate;

    public Object getPayroll(String payrollId) {
        ResponseEntity<Object> restExchange =
                restTemplate.exchange(
                        "http://payrollservice/v1/organizations/{payrollId}", //use logical name registered with netflix
                        HttpMethod.GET,
                        null, Object.class, payrollId);

        return restExchange.getBody();
    }
}
