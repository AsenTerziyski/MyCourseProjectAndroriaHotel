package com.example.myproject.service.impl;

import com.example.myproject.model.binding.ReviewSendBindingModel;
import com.example.myproject.model.entities.GuestEntity;
import com.example.myproject.model.entities.MessageEntity;
import com.example.myproject.model.entities.ReviewEntity;
import com.example.myproject.repository.GuestRepository;
import com.example.myproject.repository.MessageRepository;
import com.example.myproject.repository.ReviewRepository;
import com.example.myproject.service.GuestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class GuestServiceImpl implements GuestService {
    private final GuestRepository guestRepository;
    private final MessageRepository messageRepository;
    private final ReviewRepository reviewRepository;

    public GuestServiceImpl(GuestRepository guestRepository, MessageRepository messageRepository, ReviewRepository reviewRepository) {
        this.guestRepository = guestRepository;
        this.messageRepository = messageRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public boolean receiveEmailAndMessageAndCreatesNewGuestByEmailIfNotExistsOrAddsMessageToExistingGuest(String email, String text) {

        GuestEntity guestEntity = this.guestRepository.findByEmail(email).orElse(null);

        if (guestEntity == null) {
            int random = getRandom();
            String username = createUsername(email, random);
            GuestEntity newGuest = new GuestEntity();
            newGuest.setUsername(username).setEmail(email);
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setMessageText(text);
            GuestEntity savedNewGuest = this.guestRepository.save(newGuest);
            messageEntity.setGuest(savedNewGuest);
            messageEntity.setPosted(LocalDate.now());
            this.messageRepository.save(messageEntity);
            return true;
        }
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessageText(text);
        messageEntity.setPosted(LocalDate.now());
        messageEntity.setGuest(guestEntity);
        this.messageRepository.save(messageEntity);
        return false;
    }


    @Override
    public GuestEntity createNewGuestByEmailIfNotExistsAndReturnsHimOrReturnsExistingGuestByEmail(String email) {
        GuestEntity guestEntity = this.guestRepository.findByEmail(email).orElse(null);

        if (guestEntity == null) {
            int random = getRandom();
            String username = createUsername(email, random);
            GuestEntity newGuest = new GuestEntity();
            newGuest.setUsername(username).setEmail(email);
            GuestEntity savedNewGuest = this.guestRepository.save(newGuest);
            return savedNewGuest;
        }

        return guestEntity;
    }

    @Override
    public boolean checkIfUserIsInAndroriaDBByEmail(String email) {
        GuestEntity guestEntity = this.guestRepository.findByEmail(email).orElse(null);
        if (guestEntity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void addReview(ReviewSendBindingModel reviewSendBindingModel) {

        String email = reviewSendBindingModel.getEmail();
        GuestEntity guest = createNewGuestByEmailIfNotExistsAndReturnsHimOrReturnsExistingGuestByEmail(email);

        ReviewEntity newReview = new ReviewEntity();
        newReview.setReviewText(reviewSendBindingModel.getText());
        newReview.setGuest(guest);
        newReview.setReviewerName(reviewSendBindingModel.getName());
        this.reviewRepository.save(newReview);
        guest.getReviews().add(newReview);
        this.guestRepository.save(guest);
    }

//    @Override
//    public List<GuestEntity> findAllVipGuestsWithBookingsHigherThan3() {
//        return this.guestRepository.findAllGuestsWithBookingsHigherThan3();
//    }

    @Override
    public List<GuestEntity> findAllGuestsWhoHaveBookings() {
        List<GuestEntity> allByListOfBookingsIdsIsNotNull = this.guestRepository.findAllByListOfBookingsIdsIsNotNull();
        return allByListOfBookingsIdsIsNotNull;
    }

    private String createUsername(String email, int random) {
        String[] split = email.split("@");
        String firstPartOfEmail = split[0];
        return firstPartOfEmail + random;
    }

    private int getRandom() {
        Random rand = new Random();
        int upperbound = 1001;
        return rand.nextInt(upperbound);
    }

}
