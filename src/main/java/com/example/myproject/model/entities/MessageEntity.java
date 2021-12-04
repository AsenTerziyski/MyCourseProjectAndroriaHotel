package com.example.myproject.model.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "messages")
public class MessageEntity extends BaseEntity {

    private String messageText;
    private LocalDate posted;
    private GuestEntity guest;

    public MessageEntity() {
    }

//    @Column(columnDefinition = "LONGTEXT")
    @Lob
    public String getMessageText() {
        return messageText;
    }

    public MessageEntity setMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    @ManyToOne
    public GuestEntity getGuest() {
        return guest;
    }

    public MessageEntity setGuest(GuestEntity guest) {
        this.guest = guest;
        return this;
    }

    public LocalDate getPosted() {
        return posted;
    }

    public MessageEntity setPosted(LocalDate posted) {
        this.posted = posted;
        return this;
    }
}
