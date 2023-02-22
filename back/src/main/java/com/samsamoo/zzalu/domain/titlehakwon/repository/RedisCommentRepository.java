package com.samsamoo.zzalu.domain.titlehakwon.repository;

import com.samsamoo.zzalu.infra.redis.service.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class RedisCommentRepository {

    private Map<String, ChannelTopic> titleTopics;

    private final RedisMessageListenerContainer redisMessageListener;

    private final RedisSubscriber redisSubscriber;
    @PostConstruct
    private void init (){

        titleTopics= new HashMap<>();
        titleTopics.put("comments",new ChannelTopic("comments"));
        titleTopics.put("likes",new ChannelTopic("likes"));
        redisMessageListener.addMessageListener(redisSubscriber, titleTopics.get("comments"));
        redisMessageListener.addMessageListener(redisSubscriber, titleTopics.get("likes"));

    }

    public ChannelTopic getTopic (String topic){
        return titleTopics.get(topic);
    }


}
