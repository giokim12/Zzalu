package com.samsamoo.zzalu.domain.gifs.repository;

import com.samsamoo.zzalu.domain.gifs.entity.Gifs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GifsRepository extends JpaRepository<Gifs, Long> {
//    List<Gifs> findTop30ByOrderByLikeCountDesc();
    List<Gifs> findByTagsContains(String searchKeyword);
    List<Gifs> findTop20ByTagsContainsOrderByVisitedCountDesc(String search);
    Long countBy();
    List<Gifs> findByIdIn(List<Long> gifIds);

}

