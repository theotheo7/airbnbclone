package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.dtos.message.request.MessagePosting;
import com.schoolproject.airbnbclone.dtos.message.response.ReceivedMessageDetails;
import com.schoolproject.airbnbclone.dtos.message.response.SentMessageDetails;
import com.schoolproject.airbnbclone.exceptions.MessageException;
import com.schoolproject.airbnbclone.exceptions.UserException;
import com.schoolproject.airbnbclone.models.Message;
import com.schoolproject.airbnbclone.models.User;
import com.schoolproject.airbnbclone.repositories.MessageRepository;
import com.schoolproject.airbnbclone.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Transactional
    public void sendMessage(Authentication authentication, MessagePosting messagePosting) {
        String recipientName = messagePosting.getRecipient();

        Optional<User> optionalSender = this.userRepository.findByUsername(authentication.getName());
        Optional<User> optionalRecipient = this.userRepository.findByUsername(recipientName);

        if (optionalSender.isEmpty()) {
            throw new UserException(UserException.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        if (optionalRecipient.isEmpty()) {
            throw new UserException(UserException.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        User sender = optionalSender.get();
        User recipient = optionalRecipient.get();

        Message message = new Message(messagePosting.getTitle(), messagePosting.getBody(), sender, recipient);
        this.messageRepository.save(message);

    }

    @Transactional
    public void readUserMessage(Authentication authentication, Long id) {

        if (!this.messageRepository.existsById(id))
            throw new MessageException(MessageException.NOT_FOUND, HttpStatus.NOT_FOUND);

        if (!this.messageRepository.findRecipientById(id).equals(authentication.getName()))
            throw new UserException(UserException.USER_ACTION_FORBIDDEN, HttpStatus.FORBIDDEN);

        this.messageRepository.readMessage(id, authentication.getName());
    }

    public List<SentMessageDetails> getUserSentMessages(Authentication authentication, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("creationDate").descending());

        Page<Message> messagePage = this.messageRepository.getSentMessages(authentication.getName(), pageRequest);
        List<Message> messageList = messagePage.getContent();
        return messageList.stream()
                .map(SentMessageDetails::new)
                .collect(Collectors.toList());
    }

    public List<ReceivedMessageDetails> getUserReceivedMessages(Authentication authentication, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("creationDate").descending());
        Page<Message> messagePage = this.messageRepository.getReceivedMessages(authentication.getName(), pageRequest);
        List<Message> messageList = messagePage.getContent();
        return messageList.stream()
                .map(ReceivedMessageDetails::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUserMessage(Authentication authentication, Long id) {

        Optional<Message> optionalMessage = this.messageRepository.findById(id);

        if (optionalMessage.isEmpty()) {
            throw new MessageException(MessageException.NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        Message message = optionalMessage.get();

        if (!message.getSender().getUsername().equals(authentication.getName())) {
            throw new UserException(UserException.USER_ACTION_FORBIDDEN, HttpStatus.BAD_REQUEST);
        }

        this.messageRepository.deleteById(id);

    }
}
