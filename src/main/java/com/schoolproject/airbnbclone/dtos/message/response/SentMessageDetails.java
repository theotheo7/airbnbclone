package com.schoolproject.airbnbclone.dtos.message.response;

import com.schoolproject.airbnbclone.models.Message;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SentMessageDetails implements Serializable {

    private final Long id;
    private final String title;
    private final String body;
    private final LocalDate creationDate;
    private final String sender;
    private final String recipient;

    public SentMessageDetails(Message message) {
        this.id = message.getId();
        this.title = message.getTitle();
        this.body = message.getBody();
        this.creationDate = message.getCreationDate();
        this.sender = message.getSender().getUsername();
        this.recipient = message.getRecipient().getUsername();
    }

}
