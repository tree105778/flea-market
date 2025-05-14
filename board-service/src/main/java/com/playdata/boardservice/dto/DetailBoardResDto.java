package com.playdata.boardservice.dto;

import com.playdata.boardservice.entity.TradeStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailBoardResDto {

    private String userName;
    private String title;
    private Long price;
    private String content;
    private TradeStatus status;
    private String sido;
    private String sigungu;
    private String dong;
    private List<String> tags;
    private String category;
    private String imageUrl;
    private LocalDateTime date;

}
