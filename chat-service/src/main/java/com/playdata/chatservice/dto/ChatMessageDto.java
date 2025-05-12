package com.playdata.chatservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChatMessageDto {

//    private String sender;
    private String receiverName;
    private String receiverEmail;
    private String content;

}
