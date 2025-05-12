package com.playdata.transactionservice.client;

import com.playdata.transactionservice.common.dto.CommonResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/user")
    CommonResDto<Long> getUser(@RequestParam("userId") Long userId);
}
