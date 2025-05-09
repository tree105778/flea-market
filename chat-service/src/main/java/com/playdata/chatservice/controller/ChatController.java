package com.playdata.chatservice.controller;

import com.playdata.chatservice.dto.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @MessageMapping("/messages")
    @SendTo("/sub/message")
    public ChatMessage chat(@RequestBody ChatMessage chatMessage) {
        return chatMessage;
    }
}
