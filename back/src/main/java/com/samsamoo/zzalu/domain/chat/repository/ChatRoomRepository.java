package com.samsamoo.zzalu.domain.chat.repository;

import com.samsamoo.zzalu.domain.chat.entity.ChatRoom;
import com.samsamoo.zzalu.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomId(String roomId);
    List<ChatRoom> findTop10ByOrderByLikeCountDesc();
    List<ChatRoom> findByTagsContainsOrRoomNameContains(String keyword1, String keyword2);
    List<ChatRoom> findByTagsContains(String keyword1);
    List<ChatRoom> findByRoomNameContains(String keyword1);
    List<ChatRoom> findAllByMemberId(Long memberId);
    List<ChatRoom> findAllByMemberIdOrderByLastActivationDesc(Long memberId);
    List<ChatRoom> findAllByMemberIdOrderByLikeCountDesc(Long memberId);
    List<ChatRoom> findAllByTagsContainsOrRoomNameContainsOrderByLastActivationDesc(String keyword1, String keyword2);
    List<ChatRoom> findAllByTagsContainsOrRoomNameContainsOrderByLikeCountDesc(String keyword1, String keyword2);
    List<ChatRoom> findAllByMemberIdAndTagsContainsOrRoomNameContainsOrderByLastActivationDesc(Long memberId, String keyword1, String keyword2);
    List<ChatRoom> findAllByMemberIdAndTagsContainsOrRoomNameContainsOrderByLikeCountDesc(Long memberId, String keyword1, String keyword2);
    List<ChatRoom> findAllByLikeMembersInAndTagsContainsOrRoomNameContainsOrderByLastActivationDesc(List<Member> member, String keyword1, String keyword2);
    List<ChatRoom> findAllByLikeMembersInAndTagsContainsOrRoomNameContainsOrderByLikeCountDesc(List<Member> member, String keyword1, String keyword2);

}
