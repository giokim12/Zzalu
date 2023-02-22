package com.samsamoo.zzalu.domain.award.repository;

import com.samsamoo.zzalu.domain.award.RankDtoInterface;
import com.samsamoo.zzalu.domain.award.entity.AwardRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AwardRecordRepository  extends JpaRepository<AwardRecord , Long> {

    @Query(value="SELECT count(CASE WHEN rank = 1 THEN 1 END) as 1st \r\n" +
            "     , count(CASE WHEN rank = 2 THEN 1 END) as 2nd \r\n" +
            "     , count(CASE WHEN rank = 3 THEN 1 END) as 3rd \r\n" +
            "  FROM award_record ar \r\n" +
            " WHERE ar.member_id = ?1", nativeQuery=true)
    RankDtoInterface getAwardRecordByMemberId(Long id);


    List<AwardRecord> findByMember_IdOrderByIdDesc(Long memberId);
    List<AwardRecord> findByMember_IdOrderById(Long memberId);


}


