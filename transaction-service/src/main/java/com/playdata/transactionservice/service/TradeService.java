package com.playdata.transactionservice.service;

import com.playdata.transactionservice.client.ProductServiceClient;
import com.playdata.transactionservice.dto.TradeReqDto;
import com.playdata.transactionservice.dto.TradeResDto;
import com.playdata.transactionservice.entity.Trade;
import com.playdata.transactionservice.entity.TradeStatus;
import com.playdata.transactionservice.repository.TradeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final ProductServiceClient productClient;

    public TradeResDto startTrade(TradeReqDto tradeReqDto) {
        Long productId = productClient.updateProductStatus(tradeReqDto.getProductId());

        return tradeRepository.save(Trade.builder()
                .userName(tradeReqDto.getUserName())
                .productId(productId)
                .build()).fromEntity();
    }

    public TradeResDto cancel(Long tradeId) {
        Trade foundTrade = tradeRepository.findById(tradeId).orElseThrow(
                () -> new EntityNotFoundException("trade not found")
        );

        foundTrade.setStatus(TradeStatus.CANCELLED);

        return foundTrade.fromEntity();
    }

    public TradeResDto retrade(Long tradeId) {
        Trade foundTrade = tradeRepository.findById(tradeId).orElseThrow(
                () -> new EntityNotFoundException("trade not found")
        );

        foundTrade.setStatus(TradeStatus.COMPLETED);

        return foundTrade.fromEntity();
    }
}
