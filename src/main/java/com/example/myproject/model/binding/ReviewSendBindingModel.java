package com.example.myproject.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class ReviewSendBindingModel {

    private String name;
    private String email;
    private String text;

    public ReviewSendBindingModel() {
    }


    public String getName() {
        return name;
    }

    public ReviewSendBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public ReviewSendBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Size(min = 5)
    public String getText() {
        return text;
    }

    public ReviewSendBindingModel setText(String text) {
        this.text = text;
        return this;
    }
}
