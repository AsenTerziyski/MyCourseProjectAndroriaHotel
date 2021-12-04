package com.example.myproject.web.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Can not find user with this username!")
public class UsernameNotFoundException extends RuntimeException {
    private final String username;

    public UsernameNotFoundException(String username) {
        super("Can not find user with username: " + username + "!");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

