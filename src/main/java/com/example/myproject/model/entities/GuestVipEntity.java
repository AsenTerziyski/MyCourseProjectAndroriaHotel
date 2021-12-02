package com.example.myproject.model.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class GuestVipEntity extends BaseEntity {

    private Long originalId;
    private String email;
    private String username;
    private int numberOfBookings;
    private String bookingsListId;

    public GuestVipEntity() {
    }

    public Long getOriginalId() {
        return originalId;
    }

    public GuestVipEntity setOriginalId(Long originalId) {
        this.originalId = originalId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public GuestVipEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public GuestVipEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getNumberOfBookings() {
        return numberOfBookings;
    }

    public GuestVipEntity setNumberOfBookings(int numberOfBookings) {
        this.numberOfBookings = numberOfBookings;
        return this;
    }

//    @Column(columnDefinition = "TEXT")
    @Lob
    public String getBookingsListId() {
        return bookingsListId;
    }

    public GuestVipEntity setBookingsListId(String bookingsListId) {
        this.bookingsListId = bookingsListId;
        return this;
    }
}
