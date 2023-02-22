package com.samsamoo.zzalu.domain.gifs.entity;

import com.samsamoo.zzalu.domain.tempGif.entity.TempGif;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gifs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="GIF_ID")
    private Long id;

    @Column(name="GIF_PATH", nullable = false, unique = true ,length = 10000)
    private String gifPath;

//    @Column(name="USE_COUNT", nullable = false)
//    @ColumnDefault("0")
//    @Builder.Default
//    @NotNull
//    private Long useCount = 0L;
//
//    @Column(name="DOWNLOAD_COUNT", nullable = false)
//    @ColumnDefault("0")
//    @Builder.Default
//    @NotNull
//    private Long downloadCount = 0L;
//
//    @Column(name="LIKE_COUNT", nullable = false)
//    @ColumnDefault("0")
//    @Builder.Default
//    @NotNull
//    private Long likeCount = 0L;

    @Column(name="TAGS")
    private String tags;

    @Column(name="SORUCES")
    private String sources;

    @Column(name="SORUCES_POST_URL")
    private String sourcesPostUrl;

    @Column(name="SORUCES_TLD")
    private String sourcesTld;

    @Column(name="IMPORT_DATETIME")
    private String importDatetime;

    @Column(name="RELATIONS_VIDEO")
    private String relationsVideo;

    @Column(name="VISITED_COUNT", nullable = false)
    @ColumnDefault("0")
    @Builder.Default
    @NotNull
    private Long visitedCount = 0L;

//    @Column(name="SCRAP_COUNT", nullable = false)
//    @ColumnDefault("0")
//    @Builder.Default
//    @NotNull
//    private Long scrapCount = 0L;

    @Column(name="SOURCE_TYPE")
    @ColumnDefault("0")
    @Builder.Default
    @NotNull
    private int sourceType = 0;

    @Column(name="DESCRIPTION")
    private String description;
    @Builder.Default
    private LocalDateTime lastUpdateTime = LocalDateTime.now();
    private String lastUpdateUsername;

    public void update(TempGif tempGif) {
        System.out.println(tempGif.toString());
        if (tempGif.getDescription() != null) {
            this.description = tempGif.getDescription();
        }
        if (tempGif.getTags() != null) {
            this.tags = tempGif.getTags();
        }
        if (tempGif.getRelationsVideo() != null) {
            this.relationsVideo = tempGif.getRelationsVideo();
        }
        this.lastUpdateTime = LocalDateTime.now();
        this.lastUpdateUsername = tempGif.getWriterUsername();
    }
//    public void increaseScrap() {
//        this.scrapCount++;
//    }

//    public void decreaseScrap() {
//        this.scrapCount--;
//    }

    public void increaseVisitedCount() {
        this.visitedCount++;
    }
}
