package com.crotontech.authserver.model;

import lombok.Data;

@Data
public class AppUser {
    private Long id;
    private String username, password;
    private String role;
    private String email;

    public AppUser(Long id, String username, String password, String role,String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email=email;
    }
}
