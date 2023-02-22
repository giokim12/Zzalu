package com.samsamoo.zzalu.domain.statistics.service;

import com.samsamoo.zzalu.domain.statistics.entity.MemberTagStatistics;
import com.samsamoo.zzalu.domain.statistics.repository.MemberTagStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberTagStatisticsService {

    private final MemberTagStatisticsRepository memberTagStatisticsRepository;

    public Optional<MemberTagStatistics> findByTagAndMemberId(String tag, Long memberId) {
        return memberTagStatisticsRepository.findByTagAndMemberId(tag, memberId);
    }

    public List<MemberTagStatistics> findTop4ByMemberId(Long memberId) {

        return memberTagStatisticsRepository.findTop4ByMemberIdOrderByCountDesc(memberId);
    }

    public void save(MemberTagStatistics memberTagStatistics) {
        memberTagStatisticsRepository.save(memberTagStatistics);
    }
}
