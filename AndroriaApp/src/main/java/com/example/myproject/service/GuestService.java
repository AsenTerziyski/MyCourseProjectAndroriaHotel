package com.example.myproject.service;

import com.example.myproject.model.binding.ReviewSendBindingModel;
import com.example.myproject.model.entities.GuestEntity;

import java.util.List;

public interface GuestService {

    boolean receiveEmailAndMessageAndCreatesNewGuestByEmailIfNotExistsOrAddsMessageToExistingGuest(String email, String text);
    GuestEntity createNewGuestByEmailIfNotExistsAndReturnsHimOrReturnsExistingGuestByEmail(String email);

    boolean checkIfUserIsInAndroriaDBByEmail(String email);

    void addReview(ReviewSendBindingModel reviewSendBindingModel);

//    List<GuestEntity> findAllVipGuestsWithBookingsHigherThan3();

    List<GuestEntity> findAllGuestsWhoHaveBookings();
}
