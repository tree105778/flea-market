package com.playdata.productservice.exception;

import com.playdata.productservice.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleProductNotFoundException(ProductNotFoundException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .statusMessage("상품을 찾을 수 없습니다.")
                .result(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}