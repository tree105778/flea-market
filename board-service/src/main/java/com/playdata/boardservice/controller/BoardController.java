package com.playdata.boardservice.controller;

import com.playdata.boardservice.common.auth.TokenUserInfo;
import com.playdata.boardservice.common.dto.CommonResDto;
import com.playdata.boardservice.dto.BoardFormReqDto;
import com.playdata.boardservice.dto.BoardListResDto;
import com.playdata.boardservice.dto.BoardResDto;
import com.playdata.boardservice.dto.DetailBoardResDto;
import com.playdata.boardservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResDto<BoardResDto>> createBoard(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @ModelAttribute BoardFormReqDto reqDto,
            @RequestParam(value = "files", required = false) List<MultipartFile> files
    ) {
        BoardResDto boardDto = boardService.createBoard(tokenUserInfo, reqDto, files);

        CommonResDto<BoardResDto> resDto = new CommonResDto<>(HttpStatus.CREATED.value(), "게시글 생성 완료", boardDto);

        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<CommonResDto<DetailBoardResDto>> getDetailBoard(@PathVariable Long boardId) {
        DetailBoardResDto detailBoard = boardService.getDetailBoard(boardId);

        CommonResDto<DetailBoardResDto> resDto = new CommonResDto<>(HttpStatus.OK.value(), boardId + "번 게시글 조회 완료", detailBoard);

        return ResponseEntity.ok(resDto);
    }

    @GetMapping("/boards")
    public ResponseEntity<CommonResDto<BoardListResDto>> getBoardList(Pageable pageable) {
        log.info("/boards controller 작동, pageable: {}", pageable);
        BoardListResDto boardList = boardService.getBoardList(pageable);

        CommonResDto<BoardListResDto> resDto = new CommonResDto<>(HttpStatus.OK.value(), "게시글 전체 조회 완료", boardList);

        return ResponseEntity.ok().body(resDto);
    }

    @PutMapping("/{boardId}")
    public Long updateBoardStatus(@PathVariable Long boardId) {
        return boardService.updateBoardStatus(boardId);
    }

    @GetMapping("/{userName}/boards")
    public ResponseEntity<CommonResDto<List<BoardResDto>>> getUserBoardList(@PathVariable String userName) {
        List<BoardResDto> foundBoard = boardService.getUserBoardList(userName);

        CommonResDto<List<BoardResDto>> resDto = new CommonResDto<>(HttpStatus.OK.value(), "유저 게시글 조회 완료", foundBoard);

        return ResponseEntity.ok(resDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<CommonResDto<Long>> deleteBoard(
        @AuthenticationPrincipal TokenUserInfo userInfo,
        @PathVariable Long boardId
    ) {
        boardService.deleteBoard(userInfo, boardId);

        CommonResDto<Long> resDto = new CommonResDto<>(HttpStatus.OK.value(), "게시글 삭제 완료", boardId);

        return ResponseEntity.ok().body(resDto);
    }
}
