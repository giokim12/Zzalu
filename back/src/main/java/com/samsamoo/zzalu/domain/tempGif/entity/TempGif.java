package com.samsamoo.zzalu.domain.tempGif.entity;

import com.samsamoo.zzalu.domain.member.entity.Member;
import com.samsamoo.zzalu.domain.tempGif.enums.RequestType;
import com.samsamoo.zzalu.domain.tempGif.enums.RequestTypeConverter;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TempGif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TEMP_GIF_ID")
    private Long id;
//    @Column(unique = true)
    private String gifPath;
//    @Column(nullable = false)
    private String tags;
    private String relationsVideo;
    private String description;
    private String writerUsername;
    @Builder.Default
    private Integer permittedCount = 0;
    @Convert(converter =  RequestTypeConverter.class)
    private RequestType requestType;
    @Builder.Default
    private Long originGifsId = null;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "TEMP_GIF_MEMBER", joinColumns = @JoinColumn(name = "TEMP_GIF_ID"), inverseJoinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Member> allowedMembers = new ArrayList<>();

    public void increseCount(Integer permittedCount) {
        this.permittedCount = permittedCount;
    }

    public void removeAllowedMember() {
        this.getAllowedMembers().clear();
    }
}
