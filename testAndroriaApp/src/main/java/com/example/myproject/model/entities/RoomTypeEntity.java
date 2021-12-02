package com.example.myproject.model.entities;

import com.example.myproject.model.entities.enums.RoomEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "room_types")
public class RoomTypeEntity extends BaseEntity {

    private RoomEnum type;
    private BigDecimal price;

    public RoomTypeEntity() {
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public RoomEnum getType() {
        return type;
    }

    public RoomTypeEntity setType(RoomEnum type) {
        this.type = type;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public RoomTypeEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
