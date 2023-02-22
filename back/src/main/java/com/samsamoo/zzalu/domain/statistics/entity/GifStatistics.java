package com.samsamoo.zzalu.domain.statistics.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GifStatistics {
    @Id
    @Column(name = "GIFS_STATISTICS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GIF_ID", unique = true)
    private Long gifId;

    @Column(name = "USE_COUNT")
    @Builder.Default
    private Long useCount = 0L;

    @Column(name = "DOWNLOAD_COUNT")
    @Builder.Default
    private Long downloadCount = 0L;
}
