package com.example.myproject.model.entities;

import com.example.myproject.model.entities.enums.RoomEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "offers")
public class OffersEntity extends BaseEntity {

    private RoomEnum room;
    private double discount;
    private double vipDiscount;
    private String description;
    private UserEntity user;
    private long stay;

    public OffersEntity() {
    }

    @Enumerated(EnumType.STRING)
    public RoomEnum getRoom() {
        return room;
    }

    public OffersEntity setRoom(RoomEnum room) {
        this.room = room;
        return this;
    }

    public double getDiscount() {
        return discount;
    }

    public OffersEntity setDiscount(double discount) {
        this.discount = discount;
        return this;
    }

    public double getVipDiscount() {
        return vipDiscount;
    }

    public OffersEntity setVipDiscount(double vipDiscount) {
        this.vipDiscount = vipDiscount;
        return this;
    }

//    @Column(columnDefinition = "LONGTEXT")
    @Lob
    public String getDescription() {
        return description;
    }

    public OffersEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    public OffersEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public long getStay() {
        return stay;
    }

    public OffersEntity setStay(long stay) {
        this.stay = stay;
        return this;
    }
}
