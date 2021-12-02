package com.example.myproject.model.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    private String username;
    private String password;
    private String fullName;
    private Boolean isAdmin = false;

    private List<OffersEntity> offers;
    private List<UserRoleEntity> roles;

    public UserEntity() {
        this.roles = new LinkedList<>();
        this.offers = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public UserEntity setAdmin(Boolean admin) {
        isAdmin = admin;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public List<OffersEntity> getOffers() {
        return offers;
    }

    public UserEntity setOffers(List<OffersEntity> offers) {
        this.offers = offers;
        return this;
    }
}
