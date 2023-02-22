package com.samsamoo.zzalu.domain.tempGif.service;

import com.samsamoo.zzalu.domain.tempGif.dto.TempGifResponse;
import com.samsamoo.zzalu.global.advice.NotFoundException;
import com.samsamoo.zzalu.infra.amazonS3.upLoader.S3Uploader;
import com.samsamoo.zzalu.global.auth.sevice.JwtTokenProvider;
import com.samsamoo.zzalu.domain.gifs.entity.Gifs;
import com.samsamoo.zzalu.domain.gifs.repository.GifsRepository;
import com.samsamoo.zzalu.domain.member.entity.Member;
import com.samsamoo.zzalu.domain.member.repository.MemberRepository;
import com.samsamoo.zzalu.domain.tempGif.dto.TempGifRequest;
import com.samsamoo.zzalu.domain.tempGif.entity.TempGif;
import com.samsamoo.zzalu.domain.tempGif.enums.RequestType;
import com.samsamoo.zzalu.domain.tempGif.repository.TempGifRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TempGifService {
    private final JwtTokenProvider jwtTokenProvider;
    private final TempGifRepository tempGifRepository;
    private final GifsRepository gifsRepository;
    private final MemberRepository memberRepository;

    private final S3Uploader s3Uploader;
    public Long createGifReq(String token, TempGifRequest request) throws IOException {
        Member writer = jwtTokenProvider.getMember(token);

        if (request.getTempGifMultipartFile() != null) {
            String tempUrl = s3Uploader.upload(request.getTempGifMultipartFile(), "TempGif");
            request.setReturnUrl(tempUrl);
        } else {
            Gifs originGif = gifsRepository.findById(request.getOriginId())
                    .orElseThrow(() -> new NotFoundException("해당 원본 gif를 찾을 수 없습니다."));
            request.setReturnUrl(originGif.getGifPath());
        }
        TempGif tempGif = tempGifRepository.save(request.toEntity(writer));
        return tempGif.getId();
    }

    public Gifs returnGifs(Long gifsId) {
        return gifsRepository.findById(gifsId)
                .orElseThrow(()-> new NotFoundException("해당 원본 gif를 찾을 수 없습니다."));
    }

    public void increaseCount(String token, Long tempId) {
        Member member = jwtTokenProvider.getMember(token);
//        checkManager(member);
        TempGif tempGif = tempGifRepository.findById(tempId)
                .orElseThrow(() -> new NotFoundException("해당 임시 게시물을 찾을 수 없습니다."));
        Integer permittedCount = tempGif.getPermittedCount()+1;
        if (permittedCount >= 3) {
            // tempGif > real Gifs
            // Create 요청이었을 때
            if (tempGif.getRequestType().equals(RequestType.CREATE)) {
                Gifs gifs = Gifs.builder()
                        .gifPath(tempGif.getGifPath())
                        .sourceType(4)
                        .tags(tempGif.getTags())
                        .relationsVideo(tempGif.getRelationsVideo())
                        .description(tempGif.getDescription())
                        .lastUpdateUsername(tempGif.getWriterUsername())
                        .build();
                gifsRepository.save(gifs);
            } else {
                // Update 요청이었을 때
                Gifs originGifs = returnGifs(tempGif.getOriginGifsId());
                originGifs.update(tempGif);
                gifsRepository.save(originGifs);
            }
            member.increaseCount();
            memberRepository.save(member);
            // tempGif 삭제
            tempGif.removeAllowedMember();
            tempGifRepository.save(tempGif);
            tempGifRepository.delete(tempGif);
        } else {
            // 그대로 저장
            tempGif.increseCount(permittedCount);
            tempGif.getAllowedMembers().add(member);
            tempGifRepository.save(tempGif);
        }
    }

    public List<TempGifResponse> getAllTempGif(String token) {
        Member member = jwtTokenProvider.getMember(token);
        List<TempGif> list = tempGifRepository.findAll();
        for(Iterator<TempGif> myItr = list.iterator(); myItr.hasNext();) {
            TempGif tempGif = myItr.next();
            if (tempGif.getAllowedMembers().contains(member)) {
                myItr.remove();
            }
        }
        List<TempGifResponse> responses = new ArrayList<>();
        for (TempGif tempGif : list) {
           responses.add(new TempGifResponse(tempGif));
        }
        Collections.reverse(responses);
        return responses;
    }

    public void deleteTempGif(Long tempId) {
//        checkManager(jwtTokenProvider.getMember(token));
        TempGif tempGif = tempGifRepository.findById(tempId)
                .orElseThrow(()-> new NotFoundException("해당 임시 게시물을 찾을 수 없습니다."));
        if (tempGif.getGifPath() != null) {
            s3Uploader.delete(tempGif.getGifPath());
        }
        tempGifRepository.delete(tempGif);
    }
}
