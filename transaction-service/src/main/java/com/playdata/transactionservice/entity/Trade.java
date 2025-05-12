package com.playdata.transactionservice.entity;

import com.playdata.transactionservice.dto.TradeResDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TradeStatus status = TradeStatus.COMPLETED;

    @CreatedDate
    private LocalDateTime createTime;

    public TradeResDto fromEntity() {
        return TradeResDto.builder()
                .productId(productId)
                .userName(userName)
                .dateTime(createTime)
                .build();
    }

}
