package com.playdata.boardservice.repository;

import com.playdata.boardservice.entity.Board;
import com.playdata.boardservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard(Board board);
}
