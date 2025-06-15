package com.playdata.boardservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommentResDto {

    private Long commentId;
    private String content;
    private String author;
    private LocalDateTime createdAt;

}
