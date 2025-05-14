package com.playdata.boardservice.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardListResDto {

    private Integer totalPage;
    private Long totalElement;
    private List<BoardResDto> boards;

}
