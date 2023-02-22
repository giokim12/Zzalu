package com.samsamoo.zzalu.domain.titlehakwon.repository;

import com.samsamoo.zzalu.domain.titlehakwon.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByIdLessThanAndTitleHakwonIdOrderByIdDesc(Long lastCommentId, Long titleHackwonId , PageRequest pageRequest);
    Page<Comment> findByIdGreaterThanAndTitleHakwonId (Long lastCommentId, Long titleHackwonId , PageRequest pageRequest);

    Page<Comment> findByTitleHakwonIdAndLikeNumGreaterThanOrderByLikeNumDesc(Long titleHackwonId,int likesize ,PageRequest pageRequest);

    List<Comment> findTop3ByTitleHakwonIdAndLikeNumGreaterThanOrderByLikeNumDesc(Long titleHackwonId,int likesize);
}
