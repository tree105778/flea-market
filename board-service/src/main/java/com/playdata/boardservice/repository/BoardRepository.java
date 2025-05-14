package com.playdata.boardservice.repository;

import com.playdata.boardservice.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByUserName(String userName);
}
