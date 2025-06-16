package com.playdata.boardservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentControllerReqDto {

    private String content;
    private Long boardId;

}
