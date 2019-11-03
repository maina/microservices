package com.crotontech.common.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class MicroserviceUser extends User {
    private  Long id;
    private  String name;
    private  String email;


    public MicroserviceUser(String username, String password, boolean enabled,
                            boolean accountNonExpired, boolean credentialsNonExpired,
                            boolean accountNonLocked,
                            Collection<GrantedAuthority> authorities,
                            String email,String name,Long id) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
        this.email = email;
        this.name= name;
        this.id=id;

    }

}
