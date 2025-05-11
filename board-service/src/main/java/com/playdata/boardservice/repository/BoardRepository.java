package com.playdata.boardservice.repository;

import com.playdata.boardservice.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
