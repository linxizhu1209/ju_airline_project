package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.response.ChatRoomResponse;
import org.example.paymenttest.entity.chat.ChatMessage;
import org.example.paymenttest.entity.chat.ChatRoom;
import org.example.paymenttest.exception.ChatRoomNotFoundException;
import org.example.paymenttest.repository.chat.ChatMessageRepository;
import org.example.paymenttest.repository.chat.ChatRoomRepository;
import org.example.paymenttest.repository.UserRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;


    public void saveMessage(ChatMessage chatMessage, String role) {
        Optional<String> senderName = userRepository.findNameByEmail(chatMessage.getSender());
        String resolvedSenderName = senderName.orElse(chatMessage.getSender());

        chatMessage.setSender(resolvedSenderName);

        chatMessageRepository.save(chatMessage);
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(chatMessage.getRoomId());
        if(chatRoom.isEmpty()){
           if("ROLE_USER".equals(role)){
                ChatRoom newRoom = new ChatRoom(
                        chatMessage.getRoomId(),
                        resolvedSenderName,
                        chatMessage.getMessage(),
                        chatMessage.getTimestamp(),
                        true
                );
                System.out.println("👉 roomId: " + newRoom.getRoomId()); // 이게 null이면 문제
                mongoTemplate.save(newRoom);
                return;
            } else {
                throw new ChatRoomNotFoundException("해당 채팅방이 존재하지 않습니다.");
            }
        }

        ChatRoom room = chatRoom.get();
        room.setLastMessage(chatMessage.getMessage());
        room.setLastTimestamp(chatMessage.getTimestamp());
        room.setHasUnread("ROLE_USER".equals(role)); // user가 보낸거면 관리자입장에서는 읽지않은것이므로 true,
        mongoTemplate.save(room);
    }


    public List<ChatRoomResponse> getChatRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        List<ChatRoomResponse> chatRoomResponses = new ArrayList<>();
        for(ChatRoom chatRoom : chatRooms){
            chatRoomResponses.add(
                    ChatRoomResponse
                            .builder().roomId(chatRoom.getRoomId())
                            .sender(chatRoom.getUserName())
                            .lastMessage(chatRoom.getLastMessage())
                            .lastMessageTime(chatRoom.getLastTimestamp())
                            .build());
        }
        return chatRoomResponses;
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

    public List<ChatMessage> markMessagesAsRead(String roomId, String role, String requester){
        List<ChatMessage> messages = chatMessageRepository.findByRoomId(roomId);
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        boolean isChangedRoom = false;
        for(ChatMessage msg : messages){
            if("ROLE_ADMIN".equals(role)){
                if(!"관리자".equalsIgnoreCase(msg.getSender()) && msg.isUnread()){
                    chatRoom.setHasUnread(false);
                    msg.setUnread(false);
                }
                isChangedRoom = true;
            } else {
                if("관리자".equalsIgnoreCase(msg.getSender()) && msg.isUnread()){
                    msg.setUnread(false);
                }
            }
        }
        if(isChangedRoom) chatRoomRepository.save(chatRoom);
        chatMessageRepository.saveAll(messages);
        return messages;

    }


}
