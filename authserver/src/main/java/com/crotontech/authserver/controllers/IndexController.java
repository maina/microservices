package com.crotontech.authserver.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String getCompany( ) {


        return "welcome. This is auth service";
    }


//    @RequestMapping(value="/{organizationId}",method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteOrganization( @PathVariable("orgId") String orgId,  @RequestBody Organization org) {
//        orgService.deleteOrg( org );
//    }
}
