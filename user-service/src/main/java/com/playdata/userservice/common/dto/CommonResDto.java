package com.playdata.userservice.common.dto;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResDto<T> {
    private int statusCode;
    private String statusMessage;
    private T result;
}
