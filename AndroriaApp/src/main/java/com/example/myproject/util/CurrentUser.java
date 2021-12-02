//package com.example.myproject.util;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.annotation.SessionScope;
//
//@Component
//@SessionScope
//public class CurrentUser {
//
//    private Long id;
//    private String username;
//    private boolean isLogged;
//
//    public CurrentUser() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public CurrentUser setId(Long id) {
//        this.id = id;
//        return this;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public CurrentUser setUsername(String username) {
//        this.username = username;
//        return this;
//    }
//
//    public void cleanCurrentUser() {
//        this.id = null;
//        this.username = null;
//    }
//
//    public boolean isLogged() {
//        return this.id != null;
//    }
//
//    public void setLogged(boolean logged) {
//        isLogged = logged;
//    }
//}
