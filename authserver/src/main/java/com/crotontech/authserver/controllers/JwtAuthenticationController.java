package com.crotontech.authserver.controllers;

import com.crotontech.authserver.dto.JwtRequest;
import com.crotontech.authserver.dto.JwtResponse;
import com.crotontech.authserver.services.UserDetailsServiceImpl;
import com.crotontech.common.models.MicroserviceUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtAuthenticationController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {

        logger.debug("Authenticating user with credentials {} ", authenticationRequest);
        String token = "";
        final MicroserviceUser userDetails = (MicroserviceUser) userDetailsService
                .loadUserByUsername(authenticationRequest);

        token = userDetailsService.generateToken(userDetails);
        if (token.isEmpty()) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);

        }
        return ResponseEntity.ok(new JwtResponse(token));
    }


}
