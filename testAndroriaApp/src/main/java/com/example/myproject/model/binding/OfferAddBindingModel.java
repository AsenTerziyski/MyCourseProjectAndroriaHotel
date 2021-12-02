package com.example.myproject.model.binding;

import com.example.myproject.model.entities.enums.RoomEnum;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class OfferAddBindingModel {

    private RoomEnum room;
//    private BigDecimal price;
    private double discount;
    private double vipDiscount;
    private long stay;
    private String description;

    public OfferAddBindingModel() {
    }

    @Enumerated
    @NotNull
    public RoomEnum getRoom() {
        return room;
    }

    public OfferAddBindingModel setRoom(RoomEnum room) {
        this.room = room;
        return this;
    }


    @Positive
    @NotNull
    public double getDiscount() {
        return discount;
    }

    public OfferAddBindingModel setDiscount(double discount) {
        this.discount = discount;
        return this;
    }

    @Positive
    @NotNull
    public double getVipDiscount() {
        return vipDiscount;
    }

    @NotNull
    @Size(min = 10)
    public String getDescription() {
        return description;
    }

    public OfferAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    @Positive
    public long getStay() {
        return stay;
    }

    public OfferAddBindingModel setStay(long stay) {
        this.stay = stay;
        return this;
    }

    public OfferAddBindingModel setVipDiscount(double vipDiscount) {
        this.vipDiscount = vipDiscount;
        return this;
    }
}
