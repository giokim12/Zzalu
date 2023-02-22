package com.samsamoo.zzalu.domain.statistics.repository;

import com.samsamoo.zzalu.domain.statistics.entity.MemberTagStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberTagStatisticsRepository extends JpaRepository<MemberTagStatistics, Long> {
    Optional<MemberTagStatistics> findByTagAndMemberId(String Tag, Long memberId);
//    List<MemberTagStatistics> findAllByMemberUsername(String username);
    List<MemberTagStatistics> findTop4ByMemberIdOrderByCountDesc(Long memberId);
}
