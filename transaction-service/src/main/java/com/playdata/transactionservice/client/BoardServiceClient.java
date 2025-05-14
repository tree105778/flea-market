package com.playdata.transactionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "board-service")
public interface BoardServiceClient {

    @PutMapping("/board/{boardId}")
    Long updateBoardStatus(@PathVariable Long boardId);

}
