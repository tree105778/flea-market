package com.playdata.productservice.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.productservice.dto.ApiResponse;
import com.playdata.productservice.dto.ProductRequestDto;
import com.playdata.productservice.dto.ProductResponseDto;
import com.playdata.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<ProductResponseDto>> registerProduct(@Valid @ModelAttribute ProductRequestDto requestDto) throws IOException {
        ProductResponseDto responseDto = productService.registerProduct(requestDto);

        ApiResponse<ProductResponseDto> response = ApiResponse.<ProductResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .statusMessage("상품 등록 완료")
                .result(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .statusMessage("상품이 성공적으로 삭제되었습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@PathVariable Long productId) {
        ProductResponseDto responseDto = productService.getProduct(productId);

        ApiResponse<ProductResponseDto> response = ApiResponse.<ProductResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .statusMessage("상품 조회 결과")
                .result(responseDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> searchProducts(@RequestParam("query") String query) {
        List<ProductResponseDto> products = productService.searchProducts(query);

        ApiResponse<List<ProductResponseDto>> response = ApiResponse.<List<ProductResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .statusMessage("상품 조회 결과")
                .result(products)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProductToSoldOut(@PathVariable("productId") Long prodId) {
        ProductResponseDto updatedProduct = productService.updateSoldOut(prodId);

        ApiResponse<ProductResponseDto> response = ApiResponse.<ProductResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .statusMessage("상품 SOLD_OUT")
                .result(updatedProduct)
                .build();

        return ResponseEntity.ok(response);
    }
}
