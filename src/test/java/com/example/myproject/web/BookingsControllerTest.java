package com.example.myproject.web;

import org.junit.jupiter.api.*;
import static java.time.temporal.ChronoUnit.DAYS;
import com.example.myproject.model.entities.*;
import com.example.myproject.repository.*;
import org.junit.jupiter.api.Test;
import com.example.myproject.model.entities.enums.RoomEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
class BookingsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    RoomRepository roomRepository;


    @Test
    void getBooking() throws Exception {
        mockMvc
                .perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("stayIsNegative", false))
                .andExpect(model().attribute("roomNotSelected", false))
                .andExpect(view().name("booking_create"));
    }

    @Test
    void bookingBindingModel() throws Exception {
        LocalDate checkIn = LocalDate.of(2022, 12, 12);
        LocalDate checkOut = LocalDate.of(2022, 12, 22);
        long expectedStay = DAYS.between(checkIn, checkOut);
        int bookingsNumberBefore = this.bookingRepository.findAll().size();

        mockMvc
                .perform(post("/book/create")
                        .param("fullName", "John Smith")
                        .param("email", "test@test.com")
                        .param("phoneNumber", "+359xxxxxxxxxx")
                        .param("checkIn", String.valueOf(checkIn))
                        .param("checkOut", String.valueOf(checkOut))
                        .param("room", String.valueOf(RoomEnum.STUDIO))
                        .param("notes", "test-test-test-test-test")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("index_androria"));
        int bookingsNumberAfter = this.bookingRepository.findAll().size();
        Assertions.assertEquals(bookingsNumberBefore + 1, bookingsNumberAfter);

        List<BookingEntity> allBookings = this.bookingRepository.findAll();
        BookingEntity testBooking = new BookingEntity();
        for (int i = allBookings.size() - 1; i > 0; i--) {
            if (i == allBookings.size() - 1) {
                testBooking = allBookings.get(i);
            }
        }
        if (testBooking != null) {
            long actualStay = testBooking.getStay();
            Assertions.assertEquals(expectedStay, actualStay);
            this.bookingRepository.deleteById(testBooking.getId());
        }

        mockMvc
                .perform(post("/book/create")
                        .param("fullName", "John Smith")
                        .param("email", "test@test.com")
                        .param("phoneNumber", "+359xxxxxxxxxx")
                        .param("checkIn", String.valueOf(checkIn))
                        .param("checkOut", String.valueOf(checkOut))
                        .param("room", "wrong")
                        .param("notes", "test-test-test-test-test")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(model().attributeExists("bookingBindingModel"))
                .andExpect(status().isOk())
                .andExpect(view().name("booking_create"));

        mockMvc
                .perform(post("/book/create")
                        .param("fullName", "John Smith")
                        .param("email", "test@test.com")
                        .param("phoneNumber", "+359xxxxxxxxxx")
                        .param("checkIn", String.valueOf(checkOut))
                        .param("checkOut", String.valueOf(checkIn))
                        .param("room", String.valueOf(RoomEnum.STUDIO))
                        .param("notes", "test-test-test-test-test")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void testGetRemoveBookingPage() throws Exception {
        mockMvc
                .perform(get("/remove-booking"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("expiredBookings"))
                .andExpect(model().attributeExists("notExpiredBookings"))
                .andExpect(view().name("booking-remove"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void removeBooking() throws Exception {

        LocalDate checkIn = LocalDate.of(2022, 12, 12);
        LocalDate checkOut = LocalDate.of(2022, 12, 22);

        long stay = DAYS.between(checkIn, checkOut);
        BookingEntity testBooking = new BookingEntity();
        int numberOfBookingsBefore = this.bookingRepository.findAll().size();
        RoomTypeEntity roomType = this.roomRepository.findByType(RoomEnum.STUDIO).orElse(null);

        if (roomType != null) {
            testBooking.setRoom(roomType);
        }

        testBooking
                .setEmail("test@test.com")
                .setStay(stay)
                .setPhoneNumber("+359xxxxxxxxxx")
                .setFullName("John Smith")
                .setCheckIn(checkIn)
                .setCheckOut(checkOut)
                .setTotalPrice(BigDecimal.valueOf(1000))
                .setText("test-test-test-test-test");
        GuestEntity guest = this.guestRepository.findByEmail("test@test.com").orElse(null);

        Long testBookingId = -1L;
        if (guest != null) {
            testBooking.setGuest(guest);
            testBookingId = this.bookingRepository.save(testBooking).getId();
        }

        mockMvc
                .perform(MockMvcRequestBuilders.post("/bookings/remove/" + testBookingId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        int numberOfBookingsAfter = this.bookingRepository.findAll().size();
        Assertions.assertEquals(numberOfBookingsBefore, numberOfBookingsAfter);
        deleteTestGuest();
    }

    private void deleteTestGuest() {
        GuestEntity guestToDelete = this.guestRepository.findByEmail("test@test.com").orElse(null);
        if (guestToDelete != null) {
            this.guestRepository.delete(guestToDelete);
        }
    }
}