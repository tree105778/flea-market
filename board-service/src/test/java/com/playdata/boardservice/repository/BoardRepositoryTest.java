package com.playdata.boardservice.repository;

import com.playdata.boardservice.entity.Board;
import com.playdata.boardservice.entity.BoardTag;
import com.playdata.boardservice.entity.Tag;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach() {
        tagRepository.save(new Tag("태그1"));
        tagRepository.save(new Tag("태그2"));
        tagRepository.save(new Tag("태그3"));
        tagRepository.save(new Tag("태그4"));
    }

    @Test
    @DisplayName("board repository에서 tag 간편 추가해보기")
    void testBoardRepository() {
        Board board = Board.builder()
                .price(10000L)
                .title("게시글1")
                .content("내용1")
                .build();

        Tag tag1 = tagRepository.findByName("태그1").orElseGet(() -> tagRepository.save(new Tag("태그1")));
        Tag tag2 = tagRepository.findByName("태그2").orElseGet(() -> tagRepository.save(new Tag("태그2")));
        Tag tag3 = tagRepository.findByName("태그3").orElseGet(() -> tagRepository.save(new Tag("태그3")));
        Tag tag4 = tagRepository.findByName("태그4").orElseGet(() -> tagRepository.save(new Tag("태그4")));

        board.getTags().add(new BoardTag(board, tag1));
        board.getTags().add(new BoardTag(board, tag2));
        board.getTags().add(new BoardTag(board, tag3));
        board.getTags().add(new BoardTag(board, tag4));

        Board savedBoard = boardRepository.save(board);

        em.flush();
        em.clear();

        Board foundBoard = boardRepository.findById(savedBoard.getId()).orElseThrow();

        Assertions.assertThat(foundBoard.getTags().size()).isEqualTo(savedBoard.getTags().size());
    }
}