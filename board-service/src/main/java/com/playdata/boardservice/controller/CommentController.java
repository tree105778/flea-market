package com.playdata.boardservice.controller;

import com.playdata.boardservice.common.auth.TokenUserInfo;
import com.playdata.boardservice.common.dto.CommonResDto;
import com.playdata.boardservice.dto.CommentControllerReqDto;
import com.playdata.boardservice.dto.CommentReqDto;
import com.playdata.boardservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommonResDto<String>> registerComment(
            @AuthenticationPrincipal TokenUserInfo token,
            @RequestBody CommentControllerReqDto dto) {

        log.info("name: {}, email: {}", token.getName(), token.getEmail());
        commentService.createComment(dto.getBoardId(), new CommentReqDto(dto.getContent()), token.getName());
        CommonResDto<String> resDto = new CommonResDto<>(200, "comment created", "OK!");

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

}
