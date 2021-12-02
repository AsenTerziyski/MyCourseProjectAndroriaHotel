package com.example.myproject.init;

import com.example.myproject.model.entities.MessageEntity;
import com.example.myproject.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class InitData implements CommandLineRunner {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoomService roomService;
    private final BookingService bookingService;
    private final OffersService offersService;
    private final ReviewService reviewService;
    private final PictureService pictureService;
    private final GuestVipService guestVipService;
    private final UserBrowserService userBrowserService;
    private final MessageService messageService;

    public InitData(UserService userService, UserRoleService userRoleService, RoomService roomService, BookingService bookingService, OffersService offersService, ReviewService reviewService, PictureService pictureService, GuestVipService guestVipService, UserBrowserService userBrowserService, MessageService messageService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roomService = roomService;
        this.bookingService = bookingService;
        this.offersService = offersService;
        this.reviewService = reviewService;
        this.pictureService = pictureService;
        this.guestVipService = guestVipService;
        this.userBrowserService = userBrowserService;
        this.messageService = messageService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        this.userRoleService.initUserRoles();
        this.userService.initUsers();
        this.roomService.initRooms();
        this.offersService.initOffers();
        this.bookingService.initBookings();
        this.reviewService.initReviews();
        this.pictureService.initPictures();
        this.guestVipService.extractAllVipGuests();
        this.messageService.initMessages();
    }
}
