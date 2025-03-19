package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).build();
        }

        Account acc = accountService.register(account);

        if (acc != null) {
            return ResponseEntity.status(200).body(acc);
        } else {
            return ResponseEntity.status(409).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account acc = accountService.login(account);

        if (acc != null) {
            return ResponseEntity.status(200).body(acc);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if (message.getMessageText().length() == 0 || message.getMessageText().length() >= 255) {
            return ResponseEntity.status(400).build();
        }

        Message msg = messageService.createMessage(message);

        if (msg == null) {
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(200).body(msg);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messages = messageService.getMessages();

        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        Message message = messageService.getMessageById(message_id);

        if (message == null) {
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(200).body(message);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int message_id) {
        Integer deletedMessages = messageService.deleteMessage(message_id);

        if (deletedMessages == 0) {
        return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(200).body(deletedMessages);
    } 

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> patchMessage(@PathVariable int message_id, @RequestBody Message message) {
        if (message.getMessageText().length() == 0 || message.getMessageText().length() >= 255) {
            return ResponseEntity.status(400).build();
        }

        Integer updatedMessages = messageService.patchMessage(message_id, message);

        if (updatedMessages == null) {
            return ResponseEntity.status(400).build();
        }

        if (updatedMessages == 0) {
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(200).body(updatedMessages);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable int account_id) {
        List<Message> messages = messageService.getMessagesByAccount(account_id);

        return ResponseEntity.status(200).body(messages);
    }
}