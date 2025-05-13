package com.playdata.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "상품명은 필수입니다.")
    private String title;

    @NotNull(message = "가격은 필수입니다.")
    private String price;

    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;

    private MultipartFile image;
}

