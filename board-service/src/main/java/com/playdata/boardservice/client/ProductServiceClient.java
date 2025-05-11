package com.playdata.boardservice.client;

import com.playdata.boardservice.common.dto.ProductReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @PostMapping("/product/create")
    Long createProduct(@RequestBody ProductReqDto productReqDto);
}
