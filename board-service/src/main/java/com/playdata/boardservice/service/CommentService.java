package com.playdata.boardservice.service;

import com.playdata.boardservice.dto.CommentReqDto;
import com.playdata.boardservice.dto.CommentResDto;
import com.playdata.boardservice.entity.Board;
import com.playdata.boardservice.entity.Comment;
import com.playdata.boardservice.repository.BoardRepository;
import com.playdata.boardservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public void createComment(Long boardId, CommentReqDto commentReqDto, String author) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(commentReqDto.getContent())
                .author(author)
                .board(board)
                .build();

        commentRepository.save(comment);
    }

    public List<CommentResDto> getCommentsByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        return commentRepository.findByBoard(board).stream()
                .map(comment -> new CommentResDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getAuthor(),
                        comment.getCreatedAt()
                )).collect(Collectors.toList());
    }


}
