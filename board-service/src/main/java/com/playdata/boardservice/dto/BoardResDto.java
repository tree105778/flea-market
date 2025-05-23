package com.playdata.boardservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BoardResDto {

    private Long boardId;
    private String title;
    private Long price;
    private String imageUrl;
    private String category;
    private String sido;
    private String sigungu;
    private String dong;
    private List<String> tags;
    private LocalDateTime date;

}
