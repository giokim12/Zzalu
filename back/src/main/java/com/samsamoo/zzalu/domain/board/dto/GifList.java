package com.samsamoo.zzalu.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GifList {
    private List<GifInfo> gifs;
}
