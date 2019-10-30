package com.crotontech.PayrollService.utils;

import com.crotontech.common.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class RestTemplateClient {

    private final RestTemplate restTemplate;

    @Value("${sample.webservices.registration}")
    private String registrationService;

    public String getPayrollCompany(String companyId, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(CommonUtils.getAuthorizationHeader(request));

        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> restExchange =
                restTemplate.exchange(
                        "http://"+registrationService+"/v1/companies/{companyId}", //use logical name registered with netflix
                        HttpMethod.GET,
                        entity, String.class, companyId);

        return restExchange.getBody();
    }
}
