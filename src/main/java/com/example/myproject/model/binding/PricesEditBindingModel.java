package com.example.myproject.model.binding;

import com.example.myproject.model.entities.enums.RoomEnum;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class PricesEditBindingModel {
    private RoomEnum room;
    private BigDecimal price;

    public PricesEditBindingModel() {
    }

    @NotNull
    public RoomEnum getRoom() {
        return room;
    }

    public PricesEditBindingModel setRoom(RoomEnum room) {
        this.room = room;
        return this;
    }

    @NotNull
    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public PricesEditBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
