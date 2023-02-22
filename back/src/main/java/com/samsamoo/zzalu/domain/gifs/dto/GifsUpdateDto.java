package com.samsamoo.zzalu.domain.gifs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GifsUpdateDto {

    private Long id;
    private String tags;
    private String sources;
    private String sourcesPostUrl;
    private String sourcesTld;
    private String relationsVideo;
    private String description;
}
