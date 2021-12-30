package com.example.myproject.model.view;

import com.example.myproject.model.entities.GuestEntity;
import com.example.myproject.model.entities.RoomTypeEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingSummaryView {
    private Long id;
    private LocalDate checkOut;
    private LocalDate checkIn;
    private String fullName;
    private String text;
    private BigDecimal price;
    private long stay;

    public BookingSummaryView() {
    }

    public Long getId() {
        return id;
    }

    public BookingSummaryView setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public BookingSummaryView setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public BookingSummaryView setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getText() {
        return text;
    }

    public BookingSummaryView setText(String text) {
        this.text = text;
        return this;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public BookingSummaryView setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BookingSummaryView setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public long getStay() {
        return stay;
    }

    public BookingSummaryView setStay(long stay) {
        this.stay = stay;
        return this;
    }
}
