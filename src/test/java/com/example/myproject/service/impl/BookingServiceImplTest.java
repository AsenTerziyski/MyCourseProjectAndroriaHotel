package com.example.myproject.service.impl;

import com.example.myproject.model.entities.BookingEntity;
import com.example.myproject.model.entities.GuestEntity;
import com.example.myproject.model.entities.RoomTypeEntity;
import com.example.myproject.model.entities.enums.RoomEnum;
import com.example.myproject.model.view.BookingSummaryView;
import com.example.myproject.repository.BookingRepository;
import com.example.myproject.service.GuestService;
import com.example.myproject.service.GuestVipService;
import com.example.myproject.service.OffersService;
import com.example.myproject.service.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    private BookingServiceImpl bookingServiceToTest;
    @Mock
    private BookingRepository mockedBookingRepository;
    @Mock
    private OffersService mockedOffersService;
    @Mock
    private GuestService mockedGuestService;
    @Mock
    private GuestVipService mockedGuestVipService;
    @Mock
    private RoomService mockedRoomService;

    private BookingEntity bookingTest1;
    private BookingEntity bookingTest2;

    @BeforeEach
    void init() {
        LocalDate checkIn = LocalDate.of(2023, 9, 18);
        LocalDate checkOut = LocalDate.of(2023, 9, 28);
        long bookingStay = DAYS.between(checkIn, checkOut);
        bookingTest1 = new BookingEntity();
        bookingTest1
                .setCheckIn(checkIn)
                .setCheckOut(checkOut)
                .setStay(bookingStay)
                .setFullName("Test Test")
                .setEmail("test@email.test")
                .setPhoneNumber("+359892062848");
        RoomTypeEntity roomType = new RoomTypeEntity();
        roomType.setType(RoomEnum.DOUBLE_ROOM);
        roomType.setPrice(BigDecimal.valueOf(100));
        bookingTest1.setRoom(roomType);
        GuestEntity guest = new GuestEntity();
        guest.setUsername("test");
        bookingTest1.setGuest(guest);

        LocalDate checkIn2 = LocalDate.of(2021, 9, 18);
        LocalDate checkOut2 = LocalDate.of(2021, 9, 28);
        long bookingStay2 = DAYS.between(checkIn, checkOut);
        bookingTest2 = new BookingEntity();
        bookingTest2
                .setCheckIn(checkIn2)
                .setCheckOut(checkOut2)
                .setStay(bookingStay2)
                .setFullName("Test Test2")
                .setEmail("test2@email.test")
                .setPhoneNumber("+359892062848")
                .setText("testTestTest2");
        RoomTypeEntity roomType2 = new RoomTypeEntity();
        roomType2.setType(RoomEnum.APARTMENT);
        roomType2.setPrice(BigDecimal.valueOf(200));
        bookingTest2.setRoom(roomType2);
        GuestEntity guest2 = new GuestEntity();
        guest.setUsername("test2");
        bookingTest2.setGuest(guest2);

    }

    void initBookingServiceToTest() {
        ModelMapper modelMapper = new ModelMapper();
        this.bookingServiceToTest = new BookingServiceImpl
                (
                        mockedBookingRepository,
                        mockedOffersService,
                        mockedGuestService,
                        mockedGuestVipService,
                        mockedRoomService,
                        modelMapper
                );
    }

    @Test
    void saveNewBooking() {
    }

    @Test
    void getAllExpiredBookings() {
        LocalDate now = LocalDate.now();
        Mockito.when(this.mockedBookingRepository.getAllByCheckOutBefore(now))
                .thenReturn(List.of(bookingTest2));
        initBookingServiceToTest();
        List<BookingSummaryView> allExpiredBookings = bookingServiceToTest.getAllExpiredBookings(now);
        assertEquals(1, allExpiredBookings.size());
        for (BookingSummaryView expiredBooking : allExpiredBookings) {
            if (expiredBooking.getFullName().equals(bookingTest2.getFullName())) {
                Assertions.assertTrue(expiredBooking.getCheckOut().isEqual(bookingTest2.getCheckOut()));
            }
        }
    }

    @Test
    void getAllNotExpiredBookings() {
        LocalDate now = LocalDate.now();
        Mockito.when(this.mockedBookingRepository.getAllByCheckOutAfter(now))
                .thenReturn(List.of(bookingTest1));
        initBookingServiceToTest();
        List<BookingSummaryView> allNotExpiredBookings = bookingServiceToTest.getAllNotExpiredBookings(now);
        assertEquals(1, allNotExpiredBookings.size());
        for (BookingSummaryView notExpiredBooking : allNotExpiredBookings) {
            if (notExpiredBooking.getFullName().equals(bookingTest1.getFullName())) {
                Assertions.assertTrue(notExpiredBooking.getCheckOut().isEqual(bookingTest1.getCheckOut()));
                assertEquals("N/A", notExpiredBooking.getText());

            }
        }
    }

}