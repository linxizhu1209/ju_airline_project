package org.example.paymenttest.repository;

import org.example.paymenttest.entity.chat.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findBySender(String sender);
    List<ChatMessage> findByRoomIdOrderByTimestampAsc(String roomId);


    int countByRoomIdAndSenderNotAndUnreadIsTrue(String roomId, String email);

    List<ChatMessage> findByRoomId(String roomId);
}
