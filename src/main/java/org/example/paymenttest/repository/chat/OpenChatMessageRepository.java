package org.example.paymenttest.repository.chat;

import org.example.paymenttest.entity.chat.OpenChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OpenChatMessageRepository extends MongoRepository<OpenChatMessage, String> {
    List<OpenChatMessage> findByRoomIdAndSendAtAfter(String roomId, LocalDateTime since);

}
