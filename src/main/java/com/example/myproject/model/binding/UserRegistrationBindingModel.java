package com.example.myproject.model.binding;

import org.hibernate.cfg.annotations.Nullability;

import javax.validation.constraints.Size;

public class UserRegistrationBindingModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserRegistrationBindingModel() {
    }

    @Size(min = 4, max = 20)
    public String getUsername() {
        return username;
    }

    public UserRegistrationBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @Size(min = 4, max = 20)
    public String getPassword() {
        return password;
    }

    public UserRegistrationBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @Size(min = 4, max = 20)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRegistrationBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
