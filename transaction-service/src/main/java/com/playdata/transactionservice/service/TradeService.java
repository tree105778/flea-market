package com.playdata.transactionservice.service;

import com.playdata.transactionservice.client.BoardServiceClient;
import com.playdata.transactionservice.client.ProductServiceClient;
import com.playdata.transactionservice.dto.TradeReqDto;
import com.playdata.transactionservice.dto.TradeResDto;
import com.playdata.transactionservice.entity.Trade;
import com.playdata.transactionservice.entity.TradeStatus;
import com.playdata.transactionservice.repository.TradeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TradeService {

    private final TradeRepository tradeRepository;
    private final ProductServiceClient productClient;
    private final BoardServiceClient boardClient;

    public TradeResDto startTrade(TradeReqDto tradeReqDto) {
        log.info("trade 시작 서비스 진입");
        Long foundProductId = boardClient.updateBoardStatus(tradeReqDto.getBoardId());

        productClient.updateProductStatus(foundProductId);

        return tradeRepository.save(Trade.builder()
                .userName(tradeReqDto.getUserName())
                .productId(foundProductId)
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
