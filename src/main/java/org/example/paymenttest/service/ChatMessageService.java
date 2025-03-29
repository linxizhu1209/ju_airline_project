package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.response.ChatRoomResponse;
import org.example.paymenttest.entity.ChatMessage;
import org.example.paymenttest.repository.ChatMessageRepository;
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

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
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
                chatRoomMap.put(sender, new ChatRoomResponse(sender, message.getMessage(), message.getTimestamp()));
            } else {
                ChatRoomResponse room = chatRoomMap.get(sender);
                room.setLastMessage(message.getMessage());
                room.setLastMessageTime(message.getTimestamp());
            }
        }
        return new ArrayList<>(chatRoomMap.values());
    }


    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }


}
