package com.playdata.boardservice.entity;

import com.playdata.boardservice.dto.BoardResDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbl_board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @Setter
    private Long productId;

    private String userName;

    private String userEmail;

    @Lob
    private String content;

    private String sido;

    private String sigungu;

    private String dong;

    private String title;

    private String category;

    private Long price;

    private String imageUrl;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BoardTag> tags = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public BoardResDto fromEntity() {
        return BoardResDto.builder()
                .boardId(id)
                .title(title)
                .price(price)
                .imageUrl(imageUrl)
                .category(category)
                .tags(tags.stream().map(boardTag -> boardTag.getTag().getName()).toList())
                .build();
    }
}
