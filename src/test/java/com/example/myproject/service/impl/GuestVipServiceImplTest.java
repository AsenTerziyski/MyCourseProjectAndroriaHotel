package com.example.myproject.service.impl;

import com.example.myproject.model.entities.GuestVipEntity;
import com.example.myproject.repository.GuestVipRepository;
import com.example.myproject.service.GuestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GuestVipServiceImplTest {

    private GuestVipRepository mockedGuestVipRepository;
    private GuestService mockedGuestService;
    private GuestVipServiceImpl serviceToTest;
    private GuestVipEntity testVip;
    private GuestVipEntity testVipTwo;

    @BeforeEach
    public void init() {
        testVip = new GuestVipEntity();
        testVip.setNumberOfBookings(5)
                .setBookingsListId("1 2 3 4 5")
                .setEmail("vip_test@vip.com")
                .setOriginalId(1L)
                .setUsername("testVip");

        testVipTwo = new GuestVipEntity();
        testVipTwo.setNumberOfBookings(4)
                .setBookingsListId("1 2 3 4")
                .setEmail("vip_test2@vip.com")
                .setOriginalId(2L)
                .setUsername("testVipTwo");

        this.mockedGuestVipRepository = Mockito.mock(GuestVipRepository.class);
        this.mockedGuestService = Mockito.mock(GuestService.class);

    }


    @Test
    void findIfGuestIsVipShouldReturnGuestAsVipIfInGuestVipDB() {
        Mockito
                .when(this.mockedGuestVipRepository.findByOriginalId(testVip.getOriginalId()))
                .thenReturn(testVip);

        serviceToTest = new GuestVipServiceImpl(mockedGuestService, mockedGuestVipRepository);

        boolean guestIsVip = serviceToTest.findIfGuestIsVip(this.mockedGuestVipRepository.findByOriginalId(testVip.getOriginalId())
                .getOriginalId());
        assertTrue(guestIsVip);
        boolean guestIsNotVip = serviceToTest.findIfGuestIsVip(100L);
        Assertions.assertFalse(guestIsNotVip);
    }

}