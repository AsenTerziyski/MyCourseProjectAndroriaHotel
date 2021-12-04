package com.example.myproject.model.view;

import com.example.myproject.model.entities.GuestEntity;
import com.example.myproject.model.entities.RoomTypeEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingSummaryView {
    private Long id;
    private LocalDate checkOut;
    private String fullName;
    private String text;

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
}
