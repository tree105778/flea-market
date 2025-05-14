package com.playdata.transactionservice.controller;

import com.playdata.transactionservice.common.dto.CommonResDto;
import com.playdata.transactionservice.dto.TradeReqDto;
import com.playdata.transactionservice.dto.TradeResDto;
import com.playdata.transactionservice.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
@Slf4j
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    public ResponseEntity<CommonResDto<TradeResDto>> startTrade(@RequestBody TradeReqDto tradeReqDto) {
        log.info("boardId: {}, userName: {}", tradeReqDto.getBoardId(), tradeReqDto.getUserName());
        TradeResDto tradeResDto = tradeService.startTrade(tradeReqDto);

        CommonResDto<TradeResDto> resDto = new CommonResDto<TradeResDto>(HttpStatus.CREATED.value(), "거래 완료됨", tradeResDto);

        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
    }

    @PostMapping("/cancel/{tradeId}")
    public ResponseEntity<CommonResDto<TradeResDto>> cancleTrade(@PathVariable Long tradeId) {

        TradeResDto tradeResDto = tradeService.cancel(tradeId);

        CommonResDto<TradeResDto> resDto = new CommonResDto<>(HttpStatus.OK.value(), "거래 취소 완료", tradeResDto);

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    @PostMapping("/retrade/{tradeId}")
    public ResponseEntity<CommonResDto<TradeResDto>> retrade(
            @PathVariable Long tradeId
    ) {
        TradeResDto tradeResDto = tradeService.retrade(tradeId);

        CommonResDto<TradeResDto> resDto = new CommonResDto<>(HttpStatus.OK.value(), "거래 취소 완료", tradeResDto);

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

}
