package com.example.myproject.model.entities;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class UserBrowser extends BaseEntity {

    private String username;
    private LocalDate localDate;

    public UserBrowser() {
    }

    public String getUsername() {
        return username;
    }

    public UserBrowser setUsername(String username) {
        this.username = username;
        return this;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public UserBrowser setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
        return this;
    }
}
