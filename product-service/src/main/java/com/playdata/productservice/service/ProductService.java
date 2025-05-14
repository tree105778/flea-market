package com.playdata.productservice.service;

import com.playdata.productservice.dto.ProductClientDto;
import com.playdata.productservice.dto.ProductRequestDto;
import com.playdata.productservice.dto.ProductResponseDto;
import com.playdata.productservice.entity.Product;
import com.playdata.productservice.entity.ProductStatus;
import com.playdata.productservice.exception.ProductNotFoundException;
import com.playdata.productservice.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final String UPLOAD_DIR = "uploads/";

    @Transactional
    public ProductResponseDto registerProduct(ProductRequestDto requestDto) throws IOException {
        // 이미지 저장
        String imageUrl = null;
        if (requestDto.getImage() != null && !requestDto.getImage().isEmpty()) {
            imageUrl = saveImage(requestDto.getImage());
        }

        // 문자열 가격을 정수로 변환
        Long price = Long.parseLong(requestDto.getPrice());

        // 상품 엔티티 생성 및 저장
        Product product = Product.builder()
                .title(requestDto.getTitle())
                .price(price)
                .category(requestDto.getCategory())
                .imageUrl(imageUrl)
                .status(ProductStatus.ON_SALE)
                .build();

        Product savedProduct = productRepository.save(product);

        return mapToResponseDto(savedProduct);
    }

    private String saveImage(MultipartFile image) throws IOException {
        // 디렉토리 생성
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 고유한 파일명 생성
        String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);

        // 파일 저장
        Files.write(filePath, image.getBytes());

        return "/images/" + filename;
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품 ID: " + productId + "를 찾을 수 없습니다."));

        return mapToResponseDto(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품 ID: " + productId + "를 찾을 수 없습니다."));

        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> searchProducts(String query) {
        List<Product> products = productRepository.searchProducts(query);

        return products.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private ProductResponseDto mapToResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .status(product.getStatus())
                .build();
    }

    @Transactional
    public ProductResponseDto updateSoldOut(Long productId) {
        Product foundProduct = productRepository.findById(productId).orElseThrow(
                () -> new EntityNotFoundException("product not found")
        );

        foundProduct.setStatus(ProductStatus.SOLD_OUT);

        return mapToResponseDto(foundProduct);
    }


    @Transactional
    public Long createProduct(@RequestBody ProductClientDto productClientDto) {

        Product product = Product.builder()
                .title(productClientDto.getTitle())
                .userEmail(productClientDto.getUserEmail())
                .category(productClientDto.getCategory())
                .price(productClientDto.getPrice())
                .imageUrl(String.join(",", productClientDto.getImage()))
                .status(ProductStatus.ON_SALE)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        Product saveProduct = productRepository.save(product);
        return saveProduct.getId();
    }
}