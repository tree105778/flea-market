package com.playdata.transactionservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeResDto {

    private String userName;
    private Long productId;
    private LocalDateTime dateTime;

}
