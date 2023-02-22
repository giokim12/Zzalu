package com.samsamoo.zzalu.domain.board.entity;

import com.samsamoo.zzalu.domain.gifs.entity.Gifs;
import com.samsamoo.zzalu.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @Column(name = "BOARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String boardName;

    @Builder.Default
    private LocalDateTime createdDate = LocalDateTime.now();


    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToMany
    @Builder.Default
    @JoinTable(name = "BOARD_GIF", joinColumns = @JoinColumn(name = "BOARD_ID"), inverseJoinColumns = @JoinColumn(name = "GIF_ID"))
    private List<Gifs> gifs = new ArrayList<Gifs>();

    public void updateBoardName(String newBoardName) {
        this.boardName = newBoardName;
    }
}
