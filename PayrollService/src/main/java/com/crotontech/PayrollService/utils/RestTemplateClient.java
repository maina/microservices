package com.crotontech.PayrollService.utils;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class RestTemplateClient {
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateClient.class);

    private final RestTemplate restTemplate;

    @Value("${sample.webservices.registration}")
    private String registrationService;

    public String getPayrollCompany(String companyId, HttpServletRequest request) {

        ResponseEntity<String> restExchange =
                restTemplate.exchange(
                        "http://" + registrationService + "/v1/companies/{companyId}", //use logical name registered with netflix
                        HttpMethod.GET,
                        null, String.class, companyId);

        return restExchange.getBody();
    }
}
