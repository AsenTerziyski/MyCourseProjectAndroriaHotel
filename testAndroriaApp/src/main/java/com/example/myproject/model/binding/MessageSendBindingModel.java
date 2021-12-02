package com.example.myproject.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class MessageSendBindingModel {
    private String email;
    private String text;

    public MessageSendBindingModel() {
    }

    @Email
    public String getEmail() {
        return email;
    }

    public MessageSendBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Size(min = 5)
    public String getText() {
        return text;
    }

    public MessageSendBindingModel setText(String text) {
        this.text = text;
        return this;
    }
}
