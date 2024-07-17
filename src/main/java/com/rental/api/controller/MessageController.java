package com.rental.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.api.dto.MessageDto;
import com.rental.api.model.Message;
import com.rental.api.service.MessageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/api/messages")
@RestController
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    // Post message
    @PostMapping("")
    public  ResponseEntity<Message> sendMessage(@RequestBody MessageDto messageDto) {
        
        Message newMessage = messageService.addNewMessage(messageDto);
        return ResponseEntity.ok(newMessage);
    }
    
}
