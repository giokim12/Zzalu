package com.samsamoo.zzalu.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GifsBoardList {
    private List<GifsBoardInfo> boards;
}
