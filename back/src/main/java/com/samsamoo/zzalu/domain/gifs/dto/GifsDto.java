package com.samsamoo.zzalu.domain.gifs.dto;

import com.samsamoo.zzalu.domain.gifs.entity.Gifs;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GifsDto {

    private Long id;

    private String gifPath;

//    private Long useCount;

//    private Long downloadCount;

//    private Long likeCount;

    private String tags;

    private String sources;

    private String sourcesPostUrl;

    private String sourcesTld;

    private String importDatetime;

    private String relationsVideo;

    private String description;

    private long visitedCount;

//    private long scrapCount;

    public GifsDto(Gifs giphyGif) {
        this.id = giphyGif.getId();
        this.gifPath = giphyGif.getGifPath();
//        this.useCount = giphyGif.getUseCount();
//        this.downloadCount = giphyGif.getDownloadCount();
//        this.likeCount = giphyGif.getLikeCount();
        this.tags = giphyGif.getTags();
        this.sources = giphyGif.getSources();
        this.sourcesPostUrl = giphyGif.getSourcesPostUrl();
        this.sourcesTld = giphyGif.getSourcesTld();
        this.importDatetime = giphyGif.getImportDatetime();
        this.relationsVideo = giphyGif.getRelationsVideo();
        this.visitedCount = giphyGif.getVisitedCount();
//        this.scrapCount = giphyGif.getScrapCount();
    }

}
