package com.crotontech.common.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

// To use this class outside. You have to 
	// 1. Define it as a bean, either by adding @Component or use @Bean to instantiate an object from it
	// 2. Use the @Autowire to ask spring to auto create it for you, and inject all the values.

// So, If you tried to create an instance manually (i.e. new JwtConfig()). This won't inject all the values. 
	// Because you didn't ask Spring to do so (it's done by you manually!).
// Also, if, at any time, you tried to instantiate an object that's not defined as a bean
	// Don't expect Spring will autowire the fields inside that class object.
	
@Data        // lombok will create getters auto.
public class JwtConfig {

	// Spring doesn't inject/autowire to "static" fields. 
	// Link: https://stackoverflow.com/a/6897406
	@Value("${microservices-security.jwt.uri:/login}")
    private String loginUri;

	@Value("${microservices-security.jwt.uri:**/refreshtoken}")
    private String refreshTokenUri;

    @Value("${microservices-security.jwt.header:Authorization}")
    private String header;

    @Value("${microservices-security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${microservices-security.jwt.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${microservices-security.jwt.secret:JwtSecretKey}")
    private String secret;
    

    
}
