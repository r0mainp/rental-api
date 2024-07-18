package com.rental.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.api.dto.MessageDto;
import com.rental.api.model.Message;
import com.rental.api.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/api/messages")
@RestController
@Tag(name = "Message API", description = "API for sending messages to the owner of a rental")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    // Post message
    @PostMapping("")
        @Operation(
        summary = "Send a message",
        description = "Send a message with the provided details",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Details of the message to be sent",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
        ),
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Message sent successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            )
        }
    )
    @SecurityRequirement(name = "bearerAuth")
    public  ResponseEntity<Message> sendMessage(@RequestBody MessageDto messageDto) {
        
        Message newMessage = messageService.addNewMessage(messageDto);
        return ResponseEntity.ok(newMessage);
    }
    
}
