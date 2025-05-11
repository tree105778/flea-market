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
    private List<String> tags;

}
