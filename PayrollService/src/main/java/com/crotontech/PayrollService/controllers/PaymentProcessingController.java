package com.crotontech.PayrollService.controllers;


import com.crotontech.PayrollService.utils.RestTemplateClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = "/v1/payroll/company")
@RequiredArgsConstructor
public class PaymentProcessingController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessingController.class);
    private final RestTemplateClient restTemplateClient;

    @RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
    public String getCompany(HttpServletRequest request,@PathVariable("companyId") String companyId) {
        logger.debug("Looking up data for org {}", companyId);


        return "testcompany payroll value is " + restTemplateClient.getPayrollCompany(companyId,request);
    }


}
