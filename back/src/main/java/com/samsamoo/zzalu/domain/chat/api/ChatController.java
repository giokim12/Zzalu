package com.samsamoo.zzalu.domain.chat.api;


import com.samsamoo.zzalu.global.auth.sevice.JwtTokenProvider;
import com.samsamoo.zzalu.domain.chat.dto.ChatMessageDto;
import com.samsamoo.zzalu.domain.chat.repository.ChatRoomRedisRepository;
import com.samsamoo.zzalu.infra.kafka.service.KafkaProducer;
import com.samsamoo.zzalu.domain.member.entity.Member;
import com.samsamoo.zzalu.infra.redis.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRedisRepository chatRoomRedisRepository;
    private final KafkaProducer kafkaProducer;
    private final JwtTokenProvider jwtTokenProvider;

    @MessageMapping("/chat/message")
    public void message(ChatMessageDto message) {

        // 토큰 검사 => 에외 발생 시 Exception
        Member requestMember = jwtTokenProvider.getMember(message.getSender());
        message.setSender(requestMember.getNickname());
        message.setMemberName(requestMember.getUsername());
        message.setProfilePath(requestMember.getProfilePath());
        message.setMemberId(requestMember.getId());
        message.setSendDate(LocalDateTime.now());

        if (ChatMessageDto.MessageType.ENTER.equals(message.getType())) {
            chatRoomRedisRepository.enterChatRoom(message.getRoomId());
        }

        // kafka topic 발행
        kafkaProducer.sendMessage(message);
        // 입장이 아닐때만 저장
        if (!ChatMessageDto.MessageType.ENTER.equals(message.getType())) {
            chatRoomRedisRepository.setChatMessage(message, message.getRoomId());
        }
//        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }
}
