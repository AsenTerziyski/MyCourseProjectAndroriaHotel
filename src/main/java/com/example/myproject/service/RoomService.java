package com.example.myproject.service;

import com.example.myproject.model.binding.PricesEditBindingModel;
import com.example.myproject.model.entities.RoomTypeEntity;
import com.example.myproject.model.entities.enums.RoomEnum;
import com.example.myproject.model.view.RoomPricesView;

import java.util.List;

public interface RoomService {
    void initRooms();
    RoomTypeEntity findRoomBy(RoomEnum roomEnum);
    List<RoomPricesView> getAllPrices();

    boolean editPrice(PricesEditBindingModel pricesEditBindingModel);
}
