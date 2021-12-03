package com.example.myproject.model.service;

public class UserRegistrationServiceModel {
    private String username;
    private String password;
    private String fullName;

    public UserRegistrationServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegistrationServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRegistrationServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
