package com.playdata.boardservice.repository;

import com.playdata.boardservice.entity.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
}
