package com.example.myproject.service;

import com.example.myproject.model.entities.MessageEntity;

import java.util.List;

public interface MessageService {
    List<MessageEntity> getAllMessagesFromContactForm();
    void initMessages();
}
