package com.crotontech.authserver.services;

import com.crotontech.authserver.controllers.IndexController;
import com.crotontech.authserver.dto.JwtRequest;
import com.crotontech.authserver.model.AppUser;
import com.crotontech.common.models.MicroserviceUser;
import com.crotontech.common.security.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service   // It has to be annotated with @Service.
public class UserDetailsServiceImpl {

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private JwtConfig jwtConfig;

//    @Value("${microservices-security.jwt.expiration}")
//    private int expiration;
//
//    @Value("${microservices-security.jwt.secret}")
//    private String secret;
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    public UserDetails loadUserByUsername(JwtRequest jwtRequest) throws UsernameNotFoundException {
        logger.debug("Encoded password from auth request is: {}",encoder.encode(jwtRequest.getPassword()));
        // hard coding the users. All passwords must be encoded.
        final List<AppUser> users = Arrays.asList(
                new AppUser(1l, "james", encoder.encode("12345"), "USER", "james@microservices-sample.com"),
                new AppUser(2l, "admin", encoder.encode("12345"), "ADMIN", "admin@microservices-sample.com")
        );
        logger.debug("Encoded password from app user is: {}",encoder.encode(users.get(0).getPassword()));

        AppUser appUser = users.stream()                // convert list to stream
                .filter((user) -> encoder.matches(jwtRequest.getPassword(), user.getPassword()) && jwtRequest.getUsername().equals(user.getUsername()))     // we dont like mkyong
                .findAny()
                .orElseThrow(() -> new UsernameNotFoundException("User with supplied credentials not found: Username: " + jwtRequest.getUsername() + " password: "+ jwtRequest.getPassword()));


        // Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
        // So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_" + appUser.getRole());

        // The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
        // And used by auth manager to verify and check user authentication.
        return new MicroserviceUser(appUser.getUsername(), appUser.getPassword(), true, false, true, true, grantedAuthorities, appUser.getEmail(), appUser.getUsername(), appUser.getId());

    }

    public String generateToken(MicroserviceUser microserviceUser) {
        if(microserviceUser==null){
            return "";
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", microserviceUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList()));
        claims.put("email", microserviceUser.getEmail());
        claims.put("id", microserviceUser.getId());
        claims.put("name", microserviceUser.getUsername());

        Long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(microserviceUser.getUsername())
                // Convert to list of strings.
                // This is important because it affects the way we get them back in the Gateway.
                .addClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
        return token;
    }


}