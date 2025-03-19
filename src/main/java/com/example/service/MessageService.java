package com.example.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.*;
import com.example.entity.*;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    
    @Autowired
    AccountRepository accountRepository;

    public Message createMessage(Message message) {
        if (!accountRepository.existsById(message.getPostedBy())) {
            return null;
        }

        return messageRepository.saveAndFlush(message);
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId) {
        return messageRepository.getByMessageId(messageId);
    }

    public Integer deleteMessage(int id) {
        return messageRepository.countAndDeleteByMessageId(id);
    }

    public Integer patchMessage(int messageId, Message message) {
        if (!messageRepository.existsById(messageId)) {
            return null;
        }

        Message msg = messageRepository.getById(messageId);
        msg.setMessageText(message.getMessageText());

        messageRepository.save(msg);

        return 1;
    }

    public List<Message> getMessagesByAccount(int postedBy) {
        return messageRepository.findAllByPostedBy(postedBy);
    }
}