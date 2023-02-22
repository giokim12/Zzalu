package com.samsamoo.zzalu.domain.titlehakwon.repository;

import com.samsamoo.zzalu.domain.titlehakwon.entity.TitleHakwon;
import com.samsamoo.zzalu.domain.titlehakwon.enums.TitleHakwonState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TitleHackwonRepository extends JpaRepository<TitleHakwon, String> {


    Optional<TitleHakwon> findTitleHakwonById(Long id);

    Optional<TitleHakwon> findTitleHakwonByOpenDate(String openDate);

    List<TitleHakwon> findTitleHakwonByState(TitleHakwonState status);
}
