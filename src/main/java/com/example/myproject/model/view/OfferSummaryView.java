package com.example.myproject.model.view;

import com.example.myproject.model.entities.UserEntity;
import com.example.myproject.model.entities.enums.RoomEnum;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;

public class OfferSummaryView {
    private Long id;
    private String room;
    private double discount;
    private double vipDiscount;
    private String description;
    private long stay;
    private String  addedBy;
    private Boolean canDelete;

    public Boolean getCanDelete() {
        return canDelete;
    }

    public OfferSummaryView setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
        return this;
    }

    public OfferSummaryView() {
    }

    public Long getId() {
        return id;
    }

    public OfferSummaryView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRoom() {
        return room;
    }

    public OfferSummaryView setRoom(String room) {
        this.room = room;
        return this;
    }

    public double getDiscount() {
        return discount;
    }

    public OfferSummaryView setDiscount(double discount) {
        this.discount = discount;
        return this;
    }

    public double getVipDiscount() {
        return vipDiscount;
    }

    public OfferSummaryView setVipDiscount(double vipDiscount) {
        this.vipDiscount = vipDiscount;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferSummaryView setDescription(String description) {
        this.description = description;
        return this;
    }

    public long getStay() {
        return stay;
    }

    public OfferSummaryView setStay(long stay) {
        this.stay = stay;
        return this;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}
