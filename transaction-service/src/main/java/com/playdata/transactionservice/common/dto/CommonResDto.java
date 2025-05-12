package com.playdata.transactionservice.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
@NoArgsConstructor
public class CommonResDto<T> {

    private int statusCode;
    private String statusMessage;
    private T result;

    public CommonResDto(int statusCode, String statusMessage, T result) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.result = result;
    }
}
