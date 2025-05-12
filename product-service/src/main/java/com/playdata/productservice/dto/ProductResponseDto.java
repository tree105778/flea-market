package com.playdata.productservice.dto;

import com.playdata.productservice.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String title;
    private Integer price;
    private String description;
    private String category;
    private String imageUrl;
    private ProductStatus status; // 판매중, 판매완료 등
}