package org.example.paymenttest.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.OpenChatParticipant;
import org.example.paymenttest.entity.chat.OpenChatRoom;
import org.example.paymenttest.repository.chat.OpenChatMessageRepository;
import org.example.paymenttest.repository.chat.OpenChatParticipantRepository;
import org.example.paymenttest.repository.chat.OpenChatRoomRepository;
import org.example.paymenttest.service.OpenChatMessageService;
import org.example.paymenttest.service.RedisReadTrackingService;
import org.example.paymenttest.util.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OpenChatBackupScheduler {

    private final OpenChatRoomRepository openChatRoomRepository;
    private final RedisReadTrackingService redisReadTrackingService;
    private final RedisTemplate<String, String> redisTemplate;
    private final OpenChatMessageService openChatMessageService;
    private final OpenChatParticipantRepository openChatParticipantRepository;
    private final StringRedisTemplate stringRedisTemplate;

    @Scheduled(fixedRate = 600_000)
    public void backupUnreadCountsToDatabase(){
        List<String> allRoomIds = openChatRoomRepository.findAll()
                .stream()
                .map(OpenChatRoom::getId)
                .collect(Collectors.toList());
        for(String roomId : allRoomIds){
            List<String> messageIds = redisReadTrackingService.getTrackedMessageIds(roomId);
            for(String messageId : messageIds){
                String redisKey = RedisKeyUtil.getUnreadUserSetKey(roomId, messageId);
                Long unreadCount = redisTemplate.opsForSet().size(redisKey);
                if (unreadCount != null){
                    openChatMessageService.updateUnreadCount(messageId, unreadCount);
                }
            }

            List<OpenChatParticipant> participants = openChatParticipantRepository.findByRoomId(roomId);
            for (OpenChatParticipant participant : participants) {
                String username = participant.getUsername();
                String lastReadKey = RedisKeyUtil.getLastReadMessageKey(participant.getRoomId(), username);
                String lastReadMessageId = stringRedisTemplate.opsForValue().get(lastReadKey);

                if (lastReadMessageId != null){
                    participant.setLastReadMessageId(lastReadMessageId);
                    openChatParticipantRepository.save(participant);
                }
            }
        }
    }
}
