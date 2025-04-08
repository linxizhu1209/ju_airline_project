package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.response.ChatRoomResponse;
import org.example.paymenttest.entity.ChatMessage;
import org.example.paymenttest.entity.ChatRoom;
import org.example.paymenttest.repository.ChatMessageRepository;
import org.example.paymenttest.repository.ChatRoomRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MongoTemplate mongoTemplate;


    public void saveMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);

        ChatRoom room = chatRoomRepository.findById(chatMessage.getRoomId())
                .orElseGet(()-> {
                    ChatRoom chatRoom = new ChatRoom(
                            chatMessage.getRoomId(),
                            chatMessage.getSender(),
                            chatMessage.getMessage(),
                            chatMessage.getTimestamp(),
                            true
                    );
                    return chatRoomRepository.save(chatRoom);
                });

        room.setLastMessage(chatMessage.getMessage());
        room.setLastTimestamp(chatMessage.getTimestamp());
        room.setHasUnread(true);
        chatRoomRepository.save(room);
    }

    public List<ChatMessage> getMessagesBySender(String sender){
        return chatMessageRepository.findBySender(sender);
    }

    public List<ChatRoomResponse> getChatRooms() {
        List<ChatMessage> allMessages = chatMessageRepository.findAll();
        Map<String, ChatRoomResponse> chatRoomMap = new HashMap<>();

        for(ChatMessage message : allMessages){
            String sender = message.getSender();
            if(!chatRoomMap.containsKey(sender)){
                chatRoomMap.put(sender, new ChatRoomResponse(message.getRoomId(), sender, message.getMessage(), message.getTimestamp()));
            } else {
                ChatRoomResponse room = chatRoomMap.get(sender);
                room.setRoomId(message.getRoomId());
                room.setLastMessage(message.getMessage());
                room.setLastMessageTime(message.getTimestamp());
            }
        }
        return new ArrayList<>(chatRoomMap.values());
    }


    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }


    public List<ChatMessage> getMessagesByRoomId(String roomId) {
        return chatMessageRepository.findByRoomIdOrderByTimestampAsc(roomId);
    }


    public boolean existsByRoomId(String roomId) {
        return mongoTemplate.exists(
                Query.query(Criteria.where("roomId").is(roomId)),
                ChatMessage.class
        );
    }

    public int getUnreadCountByEmailAndRole(String email, String role, String roomId) {
        if ("ADMIN".equalsIgnoreCase(role)) {
            return chatRoomRepository.countByHasUnreadIsTrue();
        } else {
            return chatMessageRepository.countByRoomIdAndSenderNotAndUnreadIsTrue(roomId, email);
        }
    }
}
