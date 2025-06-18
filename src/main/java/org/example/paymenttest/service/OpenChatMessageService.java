package org.example.paymenttest.service;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.request.OpenChatMessageRequest;
import org.example.paymenttest.entity.OpenChatParticipant;
import org.example.paymenttest.entity.chat.OpenChatMessage;
import org.example.paymenttest.entity.chat.OpenChatRoom;
import org.example.paymenttest.repository.chat.OpenChatMessageRepository;
import org.example.paymenttest.repository.chat.OpenChatParticipantRepository;
import org.example.paymenttest.repository.chat.OpenChatRoomRepository;
import org.example.paymenttest.util.RedisKeyUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenChatMessageService {

    private final OpenChatMessageRepository openChatMessageRepository;
    private final OpenChatRoomRepository openChatRoomRepository;
    private final OpenChatParticipantRepository openChatParticipantRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final OpenChatRoomService openChatRoomService;
    private final StringRedisTemplate stringRedisTemplate;
    private final MongoTemplate mongoTemplate;


    public boolean processMessage(OpenChatMessageRequest message, String sender){
        message.setSender(sender);
        message.setSendAt(LocalDateTime.now());

        if("TALK".equals(message.getType())){
            // 해당 room의 참가자수 - 1
            OpenChatRoom room = openChatRoomRepository.findById(message.getRoomId()).orElseThrow(RuntimeException::new);
            Long participantCount = room.getParticipantCount();
            Long unreadCount = room.getParticipantCount()-1;

            if(participantCount == 0){
                unreadCount = 0l;
            }
            OpenChatMessage openChatMessage = new OpenChatMessage(
                    null,
                    message.getRoomId(),
                    sender,
                    message.getContent(),
                    message.getSendAt(),
                    unreadCount,
                    null
            );
            openChatMessage = openChatMessageRepository.save(openChatMessage);
            message.setId(openChatMessage.getId());
            addUnreadUsersToRedis(openChatMessage);
            return true;
        }

        if("ENTER".equals(message.getType())){
            Optional<OpenChatParticipant> existing = openChatParticipantRepository.findByRoomIdAndUsername(message.getRoomId(), message.getSender());

            if(existing.isPresent()){
                return false;
            }

            OpenChatParticipant participant = new OpenChatParticipant(
                    null,
                    message.getRoomId(),
                    sender,
                    LocalDateTime.now(),
                    null
            );
            openChatParticipantRepository.save(participant);
            increaseParticipant(message.getRoomId());
            return true;
        }
        return false;
    }

    private void addUnreadUsersToRedis(OpenChatMessage message) {
        String roomId= message.getRoomId();
        String messageId = message.getId();
        String sender = message.getSender();

        String redisKey = RedisKeyUtil.getUnreadUserSetKey(roomId, messageId);

        List<OpenChatParticipant> participants = openChatParticipantRepository.findByRoomId(roomId);
        for(OpenChatParticipant participant : participants){
            if(!participant.getUsername().equals(sender)){
                redisTemplate.opsForSet().add(redisKey, participant.getUsername());
            }
        }

        redisTemplate.expire(redisKey, Duration.ofDays(1));

    }

    public void increaseParticipant(String roomId){
        OpenChatRoom room = openChatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("채팅방이 존재하지 않습니다."));
        room.setParticipantCount(room.getParticipantCount() + 1);
        openChatRoomRepository.save(room);
    }

    public List<Map<String, Object>> getMessagesSince(String roomId, LocalDateTime since, String username) {
        List<OpenChatMessage> messages = openChatMessageRepository.findByRoomIdAndSendAtAfter(roomId, since);
        List<Map<String, Object>> result = new ArrayList<>();
        String lastReadKey = RedisKeyUtil.getLastReadMessageKey(roomId, username);

        for(OpenChatMessage msg : messages){
            Map<String, Object> map = new HashMap<>();
            map.put("id", msg.getId());
            map.put("roomId", msg.getRoomId());
            map.put("sender", msg.getSender());
            map.put("content", msg.getContent());
            map.put("sendAt", msg.getSendAt());
            map.put("type", "TALK");


        String redisKey = RedisKeyUtil.getUnreadUserSetKey(msg.getRoomId(), msg.getId());
        Boolean isMember = redisTemplate.opsForSet().isMember(redisKey, username);
        if (Boolean.TRUE.equals(isMember)) {
            redisTemplate.opsForSet().remove(redisKey, username);

            stringRedisTemplate.opsForValue().set(
                    lastReadKey,
                    msg.getId()
            );
        }

        Long unreadCount = redisTemplate.opsForSet().size(redisKey);
        if (unreadCount == null){
            unreadCount = msg.getUnreadCount();
        }
        map.put("unreadCount", unreadCount);
        result.add(map);

        }
        return result;
    }


    public void markMessageAsRead(String roomId, String messageId, String username){
        String unreadKey = RedisKeyUtil.getUnreadUserSetKey(roomId, messageId);
        String lastReadKey = RedisKeyUtil.getLastReadMessageKey(roomId, username);

        Boolean isMember = redisTemplate.opsForSet().isMember(unreadKey, username);
        if(Boolean.TRUE.equals(isMember)){
            redisTemplate.opsForSet().remove(unreadKey, username);
        }
        stringRedisTemplate.opsForValue().set(lastReadKey, messageId);
    }

    public long getUnreadCount(String roomId, String messageId){
        String redisKey = RedisKeyUtil.getUnreadUserSetKey(roomId, messageId);
        return redisTemplate.opsForSet().size(redisKey);

    }

    public String getLastReadMessageId(String roomId, String username){
        String lastReadKey = RedisKeyUtil.getLastReadMessageKey(roomId, username);
        return stringRedisTemplate.opsForValue().get(lastReadKey);
    }

    // 스케쥴러
    public void updateUnreadCount(String messageId, long unreadCount){
        Query query = new Query(Criteria.where("_id").is(messageId));
        Update update = new Update().set("unreadCount", unreadCount);

        UpdateResult result = mongoTemplate.updateFirst(query, update, OpenChatMessage.class);
        if(result.getModifiedCount() == 0){
            System.out.println("unreadCount 업데이트 실패: messageId = " + messageId);
        }

    }


}
