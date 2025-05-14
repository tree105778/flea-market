package com.playdata.boardservice.service;

import com.playdata.boardservice.client.ProductServiceClient;
import com.playdata.boardservice.common.auth.TokenUserInfo;
import com.playdata.boardservice.common.config.AwsS3Config;
import com.playdata.boardservice.common.dto.ProductClientDto;
import com.playdata.boardservice.dto.BoardFormReqDto;
import com.playdata.boardservice.dto.BoardListResDto;
import com.playdata.boardservice.dto.BoardResDto;
import com.playdata.boardservice.dto.DetailBoardResDto;
import com.playdata.boardservice.entity.Board;
import com.playdata.boardservice.entity.BoardTag;
import com.playdata.boardservice.entity.Tag;
import com.playdata.boardservice.repository.BoardRepository;
import com.playdata.boardservice.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final AwsS3Config s3Config;
    private final ProductServiceClient productServiceClient;

    public BoardResDto createBoard(TokenUserInfo tokenUserInfo, BoardFormReqDto reqDto, List<MultipartFile> files) {
        List<Tag> savedTags = reqDto.getTags().stream().distinct()
                .map(tag -> tagRepository.findByName(tag).orElseGet(() -> tagRepository.save(new Tag(tag)))).toList();

        List<String> imageUrls = files.stream().map(file -> {
            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            try {
                return s3Config.uploadToS3Bucket(file.getBytes(), uniqueFileName);
            } catch (IOException e) {
                return null;
            }
        }).toList();

        Board board = Board.builder()
                .title(reqDto.getTitle())
                .content(reqDto.getContent())
                .category(reqDto.getCategory())
                .price(reqDto.getPrice())
                .userEmail(tokenUserInfo.getEmail())
                .userName(tokenUserInfo.getName())
                .sido(reqDto.getSido())
                .sigungu(reqDto.getSigungu())
                .dong(reqDto.getDong())
                .imageUrl(imageUrls.get(0))
                .build();

        savedTags.forEach(tag -> board.getTags().add(new BoardTag(board, tag)));

        ProductClientDto productClientDto = ProductClientDto.builder()
                .title(reqDto.getTitle())
                .price(reqDto.getPrice())
                .userEmail(tokenUserInfo.getEmail())
                .category(reqDto.getCategory())
                .image(imageUrls)
                .build();

        Long productId = productServiceClient.createProduct(productClientDto);

        board.setProductId(productId);

        Board savedBoard = boardRepository.save(board);

        return savedBoard.fromEntity();

    }

    public void deleteBoard(TokenUserInfo userInfo, Long boardId) {
        Board foundBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new EntityNotFoundException("no board exist")
        );

        if (!foundBoard.getUserEmail().equals(userInfo.getEmail()))
            throw new AuthorizationServiceException("no authorize");

        boardRepository.deleteById(boardId);
    }

    public DetailBoardResDto getDetailBoard(Long boardId) {
        Board foundBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new EntityNotFoundException("게시글이 존재하지 않습니다.")
        );

        return DetailBoardResDto.builder()
                .userName(foundBoard.getUserName())
                .title(foundBoard.getTitle())
                .price(foundBoard.getPrice())
                .content(foundBoard.getContent())
                .tags(foundBoard.getTags().stream().map(boardTag -> boardTag.getTag().getName()).toList())
                .category(foundBoard.getCategory())
                .imageUrl(foundBoard.getImageUrl())
                .date(foundBoard.getUpdatedAt())
                .build();

    }

    public BoardListResDto getBoardList(Pageable pageable) {
        Page<Board> boardList = boardRepository.findAll(pageable);

        return BoardListResDto.builder()
                .totalPage(boardList.getTotalPages())
                .totalElement(boardList.getTotalElements())
                .boards(boardList.getContent().stream().map(Board::fromEntity).toList())
                .build();
    }
}
