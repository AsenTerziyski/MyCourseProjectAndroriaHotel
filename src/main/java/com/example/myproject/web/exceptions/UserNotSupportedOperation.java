package com.example.myproject.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Only admin can do this operation!")
public class UserNotSupportedOperation extends RuntimeException {
    private final String username;

    public UserNotSupportedOperation(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
