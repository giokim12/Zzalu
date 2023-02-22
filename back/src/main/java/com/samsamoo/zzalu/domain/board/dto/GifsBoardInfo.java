package com.samsamoo.zzalu.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GifsBoardInfo {
    private Long id;
    private String boardName;
    private Boolean gifContainState;

}
