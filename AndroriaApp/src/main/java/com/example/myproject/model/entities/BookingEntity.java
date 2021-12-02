package com.example.myproject.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "bookings")
public class BookingEntity extends BaseEntity{

    private LocalDate checkIn;
    private LocalDate checkOut;
    private long stay;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String text;
    private RoomTypeEntity room;
    private GuestEntity guest;

    private BigDecimal totalPrice;

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public BookingEntity setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public BookingEntity setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public BookingEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BookingEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @ManyToOne
    public GuestEntity getGuest() {
        return guest;
    }

    public BookingEntity setGuest(GuestEntity guest) {
        this.guest = guest;
        return this;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BookingEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @ManyToOne
    public RoomTypeEntity getRoom() {
        return room;
    }

    public BookingEntity setRoom(RoomTypeEntity room) {
        this.room = room;
        return this;
    }

    public long getStay() {
        return stay;
    }

    public BookingEntity setStay(long stay) {
        this.stay = stay;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BookingEntity setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    @Column(columnDefinition = "LONGTEXT")
    public String getText() {
        return text;
    }

    public BookingEntity setText(String text) {
        this.text = text;
        return this;
    }
}
