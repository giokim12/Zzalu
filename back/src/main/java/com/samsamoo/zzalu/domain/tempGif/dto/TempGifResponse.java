
package com.samsamoo.zzalu.domain.tempGif.dto;

import com.samsamoo.zzalu.domain.tempGif.entity.TempGif;
import com.samsamoo.zzalu.domain.tempGif.enums.RequestType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TempGifResponse {
    private Long id;
    private String gifPath;
    private String tags;
    private String relationsVideo;
    private String description;
    private String writerUsername;
    private Integer permittedCount;
    private RequestType requestType;
    private Long originGifsId;

    public TempGifResponse(TempGif tempGif) {
        this.id = tempGif.getId();
        this.gifPath = tempGif.getGifPath();
        this.tags = tempGif.getTags();
        this.relationsVideo = tempGif.getRelationsVideo();
        this.description = tempGif.getDescription();
        this.writerUsername = tempGif.getWriterUsername();
        this.permittedCount = tempGif.getPermittedCount();
        this.requestType = tempGif.getRequestType();
        this.originGifsId = tempGif.getOriginGifsId();
    }
}
