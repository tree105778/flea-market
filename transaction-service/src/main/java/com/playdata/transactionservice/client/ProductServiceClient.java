package com.playdata.transactionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @PutMapping("/product/{prodId}")
    Long updateProductStatus(@PathVariable("prodId") Long prodId);

}
