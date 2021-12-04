package com.example.myproject.service.impl;

import com.example.myproject.model.binding.BookingBindingModel;
import com.example.myproject.model.entities.BookingEntity;
import com.example.myproject.model.entities.GuestEntity;
import com.example.myproject.model.entities.OffersEntity;
import com.example.myproject.model.entities.RoomTypeEntity;
import com.example.myproject.model.entities.enums.RoomEnum;
import com.example.myproject.model.view.BookingSummaryView;
import com.example.myproject.repository.BookingRepository;
import com.example.myproject.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final OffersService offersService;
    private final GuestService guestService;
    private final GuestVipService guestVipService;
    private final RoomService roomService;
    private final ModelMapper modelMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, OffersService offersService, GuestService guestService, GuestVipService guestVipService, RoomService roomService, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.offersService = offersService;
        this.guestService = guestService;
        this.guestVipService = guestVipService;
        this.roomService = roomService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initBookings() {
        if (this.bookingRepository.count() == 0) {

            BookingEntity expiredBooking = new BookingEntity();
            LocalDate checkIn = LocalDate.of(2020, 2, 1);
            LocalDate checkOut = LocalDate.of(2020, 2, 11);
            createSampleBookings(expiredBooking, checkIn, checkOut);

            BookingEntity expiredBooking1 = new BookingEntity();
            LocalDate checkIn1 = LocalDate.of(2020, 3, 1);
            LocalDate checkOut1 = LocalDate.of(2020, 3, 11);
            createSampleBookings(expiredBooking1, checkIn1, checkOut1);

            BookingEntity expiredBooking2 = new BookingEntity();
            LocalDate checkIn2 = LocalDate.of(2020, 4, 1);
            LocalDate checkOut2 = LocalDate.of(2020, 4, 11);
            createSampleBookings(expiredBooking2, checkIn2, checkOut2);

            BookingEntity expiredBooking3 = new BookingEntity();
            LocalDate checkIn3 = LocalDate.of(2020, 5, 1);
            LocalDate checkOut3 = LocalDate.of(2020, 5, 11);
            createSampleBookings(expiredBooking3, checkIn3, checkOut3);

            BookingEntity notExpiredBooking = new BookingEntity();
            LocalDate checkInNotExpired = LocalDate.of(2022, 2, 1);
            LocalDate checkOutNotExpired = LocalDate.of(2022, 2, 11);
            createSampleBookings(notExpiredBooking, checkInNotExpired, checkOutNotExpired);
        }

    }

    private void createSampleBookings(BookingEntity booking, LocalDate checkIn, LocalDate checkOut) {
        long st = DAYS.between(checkIn, checkOut);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setStay(st);
        RoomEnum room = RoomEnum.DOUBLE_ROOM;
        RoomTypeEntity roomType = this.roomService.findRoomBy(room);
        booking.setRoom(roomType);
        BigDecimal price = roomType.getPrice();
        double doublePrice = price.doubleValue();
        OffersEntity offerByRoomType = this.offersService.findOfferByRoomType(roomType);
        BigDecimal totalPrice = BigDecimal.valueOf(st * doublePrice);

        double discount = 0.0;
        if (offerByRoomType != null) {
            long offerStay = offerByRoomType.getStay();
            if (st > offerStay) {
                discount = offerByRoomType.getDiscount();
                totalPrice = BigDecimal.valueOf((st * doublePrice - st * doublePrice * discount / 100));
            }
        }

        booking.setTotalPrice(totalPrice);
        GuestEntity newGuestByEmailIfNotExists = this.guestService
                .createNewGuestByEmailIfNotExistsAndReturnsHimOrReturnsExistingGuestByEmail("axl@gunsnroses.com");
        booking.setGuest(newGuestByEmailIfNotExists);
        booking.setStay(st);
        booking.setText("Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit");
        booking.setFullName("Axl Rose");
        booking.setEmail("axl@gunsnroses.com");
        booking.setPhoneNumber("+359000000000");
        BookingEntity savedBooking = this.bookingRepository.save(booking);
        String listOfBookingsIds = newGuestByEmailIfNotExists.getListOfBookingsIds();

        if (listOfBookingsIds == null) {
            listOfBookingsIds="";
        }

        listOfBookingsIds = listOfBookingsIds + savedBooking.getId() + " ";
        newGuestByEmailIfNotExists.setListOfBookingsIds(listOfBookingsIds);
        newGuestByEmailIfNotExists.getBookings().add(savedBooking);
    }

    @Override
    @Transactional
    public void saveNewBooking(BookingBindingModel bookingBindingModel) {

        LocalDate checkIn = bookingBindingModel.getCheckIn();
        LocalDate checkOut = bookingBindingModel.getCheckOut();
        long bookingStay = DAYS.between(checkIn, checkOut);

        BookingEntity newBooking = this.modelMapper.map(bookingBindingModel, BookingEntity.class);

        RoomEnum room = bookingBindingModel.getRoom();
        RoomTypeEntity roomType = this.roomService.findRoomBy(room);

        newBooking.setRoom(roomType);

        BigDecimal price = roomType.getPrice();

        double doublePrice = price.doubleValue();

        OffersEntity offerByRoomType = this.offersService.findOfferByRoomType(roomType);
        BigDecimal totalPrice = BigDecimal.valueOf(bookingStay * doublePrice);

        double discount = 0.0;
        if (offerByRoomType != null) {
            long offerStay = offerByRoomType.getStay();
            if (bookingStay > offerStay) {
                discount = offerByRoomType.getDiscount();
                totalPrice = BigDecimal.valueOf((bookingStay * doublePrice - bookingStay * doublePrice * discount / 100));
            }
        }

        newBooking.setTotalPrice(totalPrice);
        GuestEntity newGuestByEmailIfNotExists = this.guestService.createNewGuestByEmailIfNotExistsAndReturnsHimOrReturnsExistingGuestByEmail(bookingBindingModel.getEmail());
        Long id = newGuestByEmailIfNotExists.getId();

        boolean guestIsVip = this.guestVipService.findIfGuestIsVip(id);

        if (offerByRoomType != null) {
            if (guestIsVip) {
                double vipDiscount = offerByRoomType.getVipDiscount();
                BigDecimal totalPriceForVips = BigDecimal.valueOf(bookingStay * doublePrice - bookingStay * doublePrice * vipDiscount / 100);
                if (newBooking.getTotalPrice().compareTo(totalPriceForVips) > 0) {
                    newBooking.setTotalPrice(totalPriceForVips);
                }
            }
        }

        newBooking.setGuest(newGuestByEmailIfNotExists);
        newBooking.setStay(bookingStay);
        newBooking.setText(bookingBindingModel.getNotes());

        BookingEntity save = this.bookingRepository.save(newBooking);


        String listOfBookingsIds = newGuestByEmailIfNotExists.getListOfBookingsIds();

        if (listOfBookingsIds == null) {
            listOfBookingsIds="";
        }

        listOfBookingsIds = listOfBookingsIds + save.getId() + " ";
        newGuestByEmailIfNotExists.setListOfBookingsIds(listOfBookingsIds);
        newGuestByEmailIfNotExists.getBookings().add(save);
    }

    @Override
    public List<BookingSummaryView> getAllExpiredBookings(LocalDate now) {
        return this.bookingRepository
                .getAllByCheckOutBefore(now)
                .stream()
                .map(bookingEntity -> this.modelMapper.map(bookingEntity, BookingSummaryView.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingSummaryView> getAllNotExpiredBookings(LocalDate now) {
        List<BookingEntity> allByCheckOutAfter = this.bookingRepository.getAllByCheckOutAfter(now);
        return allByCheckOutAfter.stream().map(bookingEntity -> {
            BookingSummaryView bookingView = this.modelMapper.map(bookingEntity, BookingSummaryView.class);
            String text = bookingEntity.getText();

            if (text == null || text.trim().isBlank()) {
                bookingView.setText("N/A");
            }

            return bookingView;
        }).collect(Collectors.toList());
    }

    @Override
//    @Transactional
    public void removeBooking(Long id) {
        this.bookingRepository.deleteById(id);
    }

}
