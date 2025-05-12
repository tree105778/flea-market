package com.playdata.transactionservice.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeReqDto {

    private String userName;
    private Long productId;
}
