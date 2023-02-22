package com.samsamoo.zzalu.domain.titlehakwon.repository;


import com.samsamoo.zzalu.domain.titlehakwon.entity.ReplyComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, String> {


    Optional<ReplyComment> findById(Long id);
    Page<ReplyComment> findByIdLessThanAndParentCommentIdOrderByIdDesc(Long lastReplyCommentId , Long parentCommentId , PageRequest pageRequest);
    Page<ReplyComment> findByIdGreaterThanAndParentCommentId(Long lastReplyCommentId , Long parentCommentId , PageRequest pageRequest);
    void deleteById (Long id);
}
