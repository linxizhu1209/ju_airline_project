package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisReadTrackingService {

    private final RedisTemplate<String, String> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public void markMessageAsRead(String roomId, String messageId, String username){
        String key = "unread:" + roomId + ":" + messageId;
        redisTemplate.opsForSet().remove(key,username);

        stringRedisTemplate.opsForValue().set(
                "last_read:" + roomId + ":" + username, messageId
        );
    }

    public List<String> getTrackedMessageIds(String roomId) {
        String pattern = "unread:" + roomId + ":*";

        Set<String> keys = stringRedisTemplate.keys(pattern);
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> messageIds = keys.stream()
                .map(key -> {
                    String[] parts = key.split(":");
                    return parts.length == 3 ? parts[2] : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return messageIds;
    }
}
