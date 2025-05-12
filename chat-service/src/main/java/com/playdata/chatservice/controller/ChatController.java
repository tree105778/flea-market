package com.playdata.chatservice.controller;

import com.playdata.chatservice.common.auth.TokenUserInfo;
import com.playdata.chatservice.dto.ChatMessageDto;
import com.playdata.chatservice.entity.ChatMessage;
import com.playdata.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    // chatId는 UUID
    @MessageMapping("/{chatId}")
    public void chat(
            @Payload ChatMessageDto chatMessage,
            @DestinationVariable UUID chatId,
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo) {
        ChatMessage processedChat = chatService.saveMessage(chatId, chatMessage, tokenUserInfo);

        template.convertAndSendToUser(chatMessage.getReceiverEmail(), "/{chatId}/queue/messages", processedChat);
    }

    @GetMapping("/{chatId}")
    public List<ChatMessage> getAllChatMessage(@PathVariable UUID chatId) {
        return chatService.getAllChatMessage(chatId);
    }
}
