package com.playdata.boardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardFormReqDto {

    private String title;
    private Long price;
    private String content;
    private String category;
    private String sido;
    private String sigungu;
    private String dong;
    private List<String> tags;

}
