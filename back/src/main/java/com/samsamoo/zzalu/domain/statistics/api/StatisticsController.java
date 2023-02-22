package com.samsamoo.zzalu.domain.statistics.api;

import com.samsamoo.zzalu.global.advice.NotFoundException;
import com.samsamoo.zzalu.global.auth.sevice.JwtTokenProvider;
import com.samsamoo.zzalu.domain.gifs.entity.Gifs;
import com.samsamoo.zzalu.domain.gifs.service.GifsService;
import com.samsamoo.zzalu.domain.member.entity.Member;
import com.samsamoo.zzalu.domain.member.repository.MemberRepository;
import com.samsamoo.zzalu.domain.statistics.entity.GifStatistics;
import com.samsamoo.zzalu.domain.statistics.entity.MemberTagStatistics;
import com.samsamoo.zzalu.domain.statistics.service.GifStatisticsService;
import com.samsamoo.zzalu.domain.statistics.service.MemberTagStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StatisticsController {

    private final GifsService gifsService;
    private final GifStatisticsService gifStatisticsService;
    private final MemberTagStatisticsService memberTagStatisticsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @PostMapping("/use")
    @ResponseBody
    public boolean updateTagUseCount(@RequestHeader(value = "Authorization")String bearerToken, @RequestParam("gifId") Long gifId){

        String token = bearerToken.substring(7);
        Member requestMember = jwtTokenProvider.getMember(token);

        if(gifsService.findById(gifId).isPresent()){
            Gifs gif = gifsService.findById(gifId).get();
            String[] tags = gif.getTags().split(",");

            for(int index = 0; index < tags.length; ++index) {
                Optional<MemberTagStatistics> optionalMemberTagStatistics = memberTagStatisticsService.findByTagAndMemberId(tags[index], requestMember.getId());

                if(optionalMemberTagStatistics.isPresent()) {
                    MemberTagStatistics memberTagStatistics = optionalMemberTagStatistics.get();
                    memberTagStatistics.setCount(memberTagStatistics.getCount() + 1);
                    memberTagStatisticsService.save(memberTagStatistics);
                } else {
                    MemberTagStatistics newMemberTagStatistics = new MemberTagStatistics();
                    newMemberTagStatistics.setMemberId(requestMember.getId());
                    newMemberTagStatistics.setTag(tags[index]);
                    newMemberTagStatistics.setCount(1L);
                    memberTagStatisticsService.save(newMemberTagStatistics);
                }
            }

            Optional<GifStatistics> optionalGifStatistics = gifStatisticsService.findByGifId(gif.getId());
            GifStatistics gifStatistics;
            if(optionalGifStatistics.isPresent()) {
                gifStatistics = optionalGifStatistics.get();
                gifStatistics.setUseCount(gifStatistics.getUseCount() + 1);
            } else {
                gifStatistics = new GifStatistics();
                gifStatistics.setUseCount(1L);
                gifStatistics.setGifId(gif.getId());
            }
            gifStatisticsService.save(gifStatistics);

            return true;
        } else {
            System.out.println("gif Error");
            return false;
        }
    }

    @PostMapping("/download")
    @ResponseBody
    public boolean updateTagDownloadCount(@RequestHeader(value = "Authorization")String bearerToken, @RequestParam("gifId") Long gifId){

        String token = bearerToken.substring(7);
        Member requestMember = jwtTokenProvider.getMember(token);

        if(gifsService.findById(gifId).isPresent()){
            Gifs gif = gifsService.findById(gifId).get();
            String[] tags = gif.getTags().split(",");

            for(int index = 0; index < tags.length; ++index) {
                Optional<MemberTagStatistics> optionalMemberTagStatistics = memberTagStatisticsService.findByTagAndMemberId(tags[index], requestMember.getId());

                if(optionalMemberTagStatistics.isPresent()) {
                    MemberTagStatistics memberTagStatistics = optionalMemberTagStatistics.get();
                    memberTagStatistics.setCount(memberTagStatistics.getCount() + 1);
                    memberTagStatisticsService.save(memberTagStatistics);
                } else {
                    MemberTagStatistics newMemberTagStatistics = new MemberTagStatistics();
                    newMemberTagStatistics.setMemberId(requestMember.getId());
                    newMemberTagStatistics.setTag(tags[index]);
                    newMemberTagStatistics.setCount(1L);
                    memberTagStatisticsService.save(newMemberTagStatistics);
                }
            }

            Optional<GifStatistics> optionalGifStatistics = gifStatisticsService.findByGifId(gif.getId());
            GifStatistics gifStatistics;
            if(optionalGifStatistics.isPresent()) {
                gifStatistics = optionalGifStatistics.get();
                gifStatistics.setDownloadCount(gifStatistics.getDownloadCount() + 1);
            } else {
                gifStatistics = new GifStatistics();
                gifStatistics.setDownloadCount(1L);
                gifStatistics.setGifId(gif.getId());
            }
            gifStatisticsService.save(gifStatistics);

            return true;
        } else {
            System.out.println("gif Error");
            return false;
        }
    }

    // AccessToken이 아니라 MemberId로 받도록 수정
    @GetMapping("/member")
    @ResponseBody
    public List<MemberTagStatistics> findAllByMemberId(@RequestParam("username") String username){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));
        return memberTagStatisticsService.findTop4ByMemberId(member.getId());
    }

    @GetMapping("/gif")
    @ResponseBody
    public GifStatistics findByGifId(@RequestParam("gifId") Long gifId){
        System.out.println(gifId);
        Optional<GifStatistics> optionalGifStatistics = gifStatisticsService.findByGifId(gifId);
        return optionalGifStatistics.orElse(null);
    }


}
