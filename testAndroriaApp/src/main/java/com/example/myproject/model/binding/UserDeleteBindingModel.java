package com.example.myproject.model.binding;

import javax.validation.constraints.Size;

public class UserDeleteBindingModel {
    private String username;

    public UserDeleteBindingModel() {
    }

    @Size(min = 4, max = 20)
    public String getUsername() {
        return username;
    }

    public UserDeleteBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

}
