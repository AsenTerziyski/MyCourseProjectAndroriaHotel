package com.example.myproject.service.impl;

import com.example.myproject.model.entities.GuestEntity;
import com.example.myproject.repository.GuestRepository;
import com.example.myproject.repository.MessageRepository;
import com.example.myproject.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GuestServiceImplTest {

    private GuestServiceImpl guestServiceToTest;
    @Mock
    private GuestRepository mockedGuestRepository;
    @Mock
    private MessageRepository mockedMessageRepository;
    @Mock
    private ReviewRepository mockedReviewRepository;

    private GuestEntity testGuest1;
    private GuestEntity testGuest2;
    private GuestEntity testGuest3;

    @BeforeEach
    void init() {
        testGuest1 = new GuestEntity();
        testGuest1.setId(2L);
        testGuest1.setBookings(null);
        testGuest1.setEmail("test1@test.com");
        testGuest1.setUsername("usernameTest1");
        testGuest1.setMessages(null);
        testGuest1.setListOfBookingsIds("1 2 3 4");
        testGuest1.setReviews(null);

        testGuest2 = new GuestEntity();
        testGuest2.setId(3L);
        testGuest2.setBookings(null);
        testGuest2.setEmail("test2@test.com");
        testGuest2.setUsername("usernameTest2");
        testGuest2.setMessages(null);
        testGuest2.setListOfBookingsIds(null);
        testGuest2.setReviews(null);

        testGuest3 = new GuestEntity();
        testGuest3.setEmail("newEmail@wrongEmal");

    }

    @Test
    void receiveEmailAndMessageAndCreatesNewGuestByEmailIfNotExistsOrAddsMessageToExistingGuest() {
        Mockito
                .when(this.mockedGuestRepository.findByEmail(testGuest1.getEmail()))
                .thenReturn(Optional.ofNullable(testGuest1));
        initializeGuestServiceForTesting();
        boolean testMessage = guestServiceToTest.receiveEmailAndMessageAndCreatesNewGuestByEmailIfNotExistsOrAddsMessageToExistingGuest(testGuest1.getEmail(), "testMessage");
        Assertions.assertFalse(testMessage);

        boolean testSecond = guestServiceToTest.receiveEmailAndMessageAndCreatesNewGuestByEmailIfNotExistsOrAddsMessageToExistingGuest("test@test.com", "test");
        Assertions.assertTrue(testSecond);
    }

    @Test
    void createNewGuestByEmailIfNotExistsAndReturnsHimOrReturnsExistingGuestByEmail() {
        Mockito
                .when(this.mockedGuestRepository.findByEmail(testGuest1.getEmail()))
                .thenReturn(Optional.ofNullable(testGuest1));
        initializeGuestServiceForTesting();
        GuestEntity actual = guestServiceToTest
                .createNewGuestByEmailIfNotExistsAndReturnsHimOrReturnsExistingGuestByEmail(testGuest1.getEmail());
        Assertions.assertEquals(actual.getEmail(), testGuest1.getEmail());
        Assertions.assertEquals(actual.getUsername(), testGuest1.getUsername());
        Assertions.assertEquals(actual.getListOfBookingsIds(), "1 2 3 4");
        Assertions.assertEquals(actual.getEmail(), testGuest1.getEmail());
        GuestEntity newGuest = guestServiceToTest
                .createNewGuestByEmailIfNotExistsAndReturnsHimOrReturnsExistingGuestByEmail("test@email.email");
        Assertions.assertNull(newGuest);
    }

    @Test
    void checkIfUserIsInAndroriaDBByEmail() {
        Mockito
                .when(this.mockedGuestRepository.findByEmail(testGuest1.getEmail()))
                .thenReturn(Optional.of(testGuest1));
        initializeGuestServiceForTesting();
        boolean userIsRegisteredInDB = guestServiceToTest.checkIfUserIsInAndroriaDBByEmail(testGuest1.getEmail());
        Assertions.assertTrue(userIsRegisteredInDB);
        boolean userIsNotRegistered = guestServiceToTest.checkIfUserIsInAndroriaDBByEmail("wrong@email.bg");
        Assertions.assertFalse(userIsNotRegistered);
    }

    @Test
    void findAllVipGuestsWithBookingsHigherThan3() {
    }

    @Test
    void findAllGuestsWhoHaveBookings() {
        List<GuestEntity> testGuestsWithBookings = List.of(testGuest1, testGuest2);
        Mockito.when(this.mockedGuestRepository.findAllByListOfBookingsIdsIsNotNull())
                .thenReturn(testGuestsWithBookings);
        initializeGuestServiceForTesting();
        List<GuestEntity> allGuestsWhoHaveBookings = guestServiceToTest.findAllGuestsWhoHaveBookings();
        for (GuestEntity guest : allGuestsWhoHaveBookings) {
            Assertions.assertTrue(testGuestsWithBookings.contains(guest));
        }

    }

    private void initializeGuestServiceForTesting() {
        guestServiceToTest = new GuestServiceImpl(
                mockedGuestRepository,
                mockedMessageRepository,
                mockedReviewRepository
        );
    }
}