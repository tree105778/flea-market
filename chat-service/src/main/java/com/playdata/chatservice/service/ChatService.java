package com.playdata.chatservice.service;

import com.playdata.chatservice.common.auth.TokenUserInfo;
import com.playdata.chatservice.dto.ChatMessageDto;
import com.playdata.chatservice.entity.ChatMessage;
import com.playdata.chatservice.entity.ChatRoom;
import com.playdata.chatservice.repository.ChatMessageRepository;
import com.playdata.chatservice.repository.ChatRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRoomRepository chatRoomRepo;
    private final ChatMessageRepository chatMessageRepo;

    public ChatMessage saveMessage(UUID chatId, ChatMessageDto chatMessage, TokenUserInfo tokenUserInfo) {
        ChatRoom currChatRoom = chatRoomRepo.findByChatId(chatId).orElseGet(() ->
                chatRoomRepo.save(ChatRoom.builder()
                        .chatId(chatId)
                        .userEmail1(tokenUserInfo.getEmail())
                        .userEmail2(chatMessage.getReceiverEmail())
                        .build()));

        return chatMessageRepo.save(
                ChatMessage.builder()
                        .chatRoom(currChatRoom)
                        .senderName(tokenUserInfo.getName())
                        .receiverName(chatMessage.getReceiverName())
                        .content(chatMessage.getContent())
                        .build()
        );
    }

    public List<ChatMessage> getAllChatMessage(UUID chatId) {
        ChatRoom chatRoom = chatRoomRepo.findByChatId(chatId).orElseThrow(() -> new EntityNotFoundException("chatroom not found"));

        return chatRoom.getMessages();
    }
}
