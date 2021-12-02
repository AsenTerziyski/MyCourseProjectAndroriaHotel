package com.example.myproject.model.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "guests")
public class GuestEntity extends BaseEntity {

    private String username;
    private String email;
    private String listOfBookingsIds;


    private List<MessageEntity> messages;
    private List<BookingEntity> bookings;
    private List<ReviewEntity> reviews;

    public GuestEntity() {
        this.messages = new LinkedList<>();
        this.bookings = new LinkedList<>();
        this.reviews = new LinkedList<>();
    }

    @Column(nullable = false)
    public String getUsername() {
        return username;
    }

    public GuestEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public GuestEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY)
    public List<MessageEntity> getMessages() {
        return messages;
    }

    public GuestEntity setMessages(List<MessageEntity> messages) {
        this.messages = messages;
        return this;
    }

    @OneToMany(mappedBy = "guest", fetch = FetchType.EAGER)
    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public GuestEntity setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
        return this;
    }

    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY)
    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public GuestEntity setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
        return this;
    }

//    @Column(columnDefinition = "TEXT")
    @Lob
    public String getListOfBookingsIds() {
        return listOfBookingsIds;
    }

    public GuestEntity setListOfBookingsIds(String listOfBookingsIds) {
        this.listOfBookingsIds = listOfBookingsIds;
        return this;
    }
}
