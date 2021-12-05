package com.example.myproject.service;

import com.example.myproject.model.binding.BookingBindingModel;
import com.example.myproject.model.view.BookingSummaryView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    void saveNewBooking(BookingBindingModel bookingBindingModel);

    List<BookingSummaryView> getAllExpiredBookings(LocalDate now);
    List<BookingSummaryView> getAllNotExpiredBookings(LocalDate now);

    void initBookings();
    void removeBooking(Long id);

}



