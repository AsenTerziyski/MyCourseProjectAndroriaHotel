package com.example.myproject.service.impl;

import com.example.myproject.model.entities.MessageEntity;
import com.example.myproject.repository.MessageRepository;
import com.example.myproject.service.GuestService;
import com.example.myproject.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final GuestService guestService;

    public MessageServiceImpl(MessageRepository messageRepository, GuestService guestService) {
        this.messageRepository = messageRepository;
        this.guestService = guestService;
    }

    @Override
    public List<MessageEntity> getAllMessagesFromContactForm() {
        return this.messageRepository.getAllMessages();
    }

    @Override
    public void initMessages() {
        if (this.messageRepository.count() == 0) {
            String email = "james@email.com";
            String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                    "laboris nisi ut aliquip ex ea commodo consequat. ";
            this.guestService.receiveEmailAndMessageAndCreatesNewGuestByEmailIfNotExistsOrAddsMessageToExistingGuest(email, text);

            email = "lars@email.com";
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                    "laboris nisi ut aliquip ex ea commodo consequat. ";
            this.guestService.receiveEmailAndMessageAndCreatesNewGuestByEmailIfNotExistsOrAddsMessageToExistingGuest(email, text);

            email = "lemmy2@email.com";
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                    "laboris nisi ut aliquip ex ea commodo consequat. ";
            this.guestService.receiveEmailAndMessageAndCreatesNewGuestByEmailIfNotExistsOrAddsMessageToExistingGuest(email, text);

            email = "kolmister@email.com";
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                    "laboris nisi ut aliquip ex ea commodo consequat. ";
            this.guestService.receiveEmailAndMessageAndCreatesNewGuestByEmailIfNotExistsOrAddsMessageToExistingGuest(email, text);
        }
    }

}
