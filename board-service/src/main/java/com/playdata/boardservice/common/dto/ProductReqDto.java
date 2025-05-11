package com.playdata.boardservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReqDto {

    private String title;
    private Long price;
    private String category;
    private List<String> image;

}
