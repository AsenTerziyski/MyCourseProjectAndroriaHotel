package com.example.myproject.service.impl;

import com.example.myproject.model.entities.RoomTypeEntity;
import com.example.myproject.model.entities.enums.RoomEnum;
import com.example.myproject.model.view.RoomPricesView;
import com.example.myproject.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {
    @Mock
    private RoomRepository mockedRoomRepository;

    private RoomServiceImpl roomServiceToTest;
    private RoomTypeEntity testRoom1;
    private RoomTypeEntity testRoom2;

    @BeforeEach
    void init() {
        RoomEnum doubleRoom = RoomEnum.DOUBLE_ROOM;
        testRoom1 = new RoomTypeEntity();
        testRoom1.setType(doubleRoom).setPrice(BigDecimal.valueOf(100));

        RoomEnum apartment = RoomEnum.APARTMENT;
        testRoom2 = new RoomTypeEntity();
        testRoom2.setType(apartment).setPrice(BigDecimal.valueOf(200));
    }

    private void initRoomServiceToTest() {
        ModelMapper modelMapper = new ModelMapper();
        roomServiceToTest = new RoomServiceImpl(mockedRoomRepository, modelMapper);
    }

    @Test
    void findRoomBy() {
        Mockito.when(mockedRoomRepository.findByType(RoomEnum.DOUBLE_ROOM))
                .thenReturn(Optional.ofNullable(testRoom1));
        initRoomServiceToTest();
        RoomTypeEntity roomBy = roomServiceToTest.findRoomBy(RoomEnum.DOUBLE_ROOM);
        Assertions.assertEquals(roomBy.getType().name(), RoomEnum.DOUBLE_ROOM.name());
    }

    @Test
    void getAllPrices() {

        List<RoomTypeEntity> testRooms = List.of(testRoom1, testRoom2);

        Mockito
                .when(this.mockedRoomRepository.findAll())
                .thenReturn(List.of(testRoom1, testRoom2));

        ModelMapper modelMapper = new ModelMapper();

        initRoomServiceToTest();

        List<RoomPricesView> expected = testRooms.stream().map(roomTypeEntity -> {
            modelMapper.map(roomTypeEntity, RoomPricesView.class);
            RoomPricesView roomPricesView = modelMapper.map(roomTypeEntity, RoomPricesView.class);
            if (roomTypeEntity.getType().name().equalsIgnoreCase("double_room")) {
                roomPricesView.setType("DOUBLE ROOM");
            } else {
                roomPricesView.setType(roomTypeEntity.getType().name());
            }
            return roomPricesView;
        }).collect(Collectors.toList());

        List<RoomPricesView> actualAllPrices = roomServiceToTest.getAllPrices();

        for (RoomPricesView actualRoomPricesView : actualAllPrices) {

            BigDecimal actualPrice = actualRoomPricesView.getPrice();

            for (RoomPricesView expectedRoomPriceView : expected) {

                BigDecimal price = expectedRoomPriceView.getPrice();

                if (actualPrice.equals(price)) {
                    Assertions.assertEquals
                            (
                                    actualRoomPricesView.getType(),
                                    expectedRoomPriceView.getType()
                            );
                }

            }
        }
    }
}