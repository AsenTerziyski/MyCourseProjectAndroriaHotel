package com.example.myproject.model.binding;

import com.example.myproject.model.entities.GuestEntity;
import com.example.myproject.model.entities.RoomTypeEntity;
import com.example.myproject.model.entities.enums.RoomEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class BookingBindingModel {

    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private RoomEnum room;
    private String notes;



    @NotNull
    @Size(min = 5, max = 20)
    public String getFullName() {
        return fullName;
    }

    public BookingBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public BookingBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BookingBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }


    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getCheckIn() {
        return checkIn;
    }

    public BookingBindingModel setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getCheckOut() {
        return checkOut;
    }

    public BookingBindingModel setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public RoomEnum getRoom() {
        return room;
    }

    public BookingBindingModel setRoom(RoomEnum room) {
        this.room = room;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public BookingBindingModel setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}
