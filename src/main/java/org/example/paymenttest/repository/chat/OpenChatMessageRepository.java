package org.example.paymenttest.repository.chat;

import org.example.paymenttest.entity.chat.OpenChatMessage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OpenChatMessageRepository extends MongoRepository<OpenChatMessage, String> {
    List<OpenChatMessage> findByRoomIdAndSendAtAfter(String roomId, LocalDateTime since);

    List<OpenChatMessage> findTopNByRoomIdAndSendAtBeforeOrderBySendAtAsc(String roomId, LocalDateTime sendAt, int next);

    List<OpenChatMessage> findTopNByRoomIdAndSendAtAfterOrderBySendAtAsc(String roomId, LocalDateTime sendAt, int next);

    List<OpenChatMessage> findTopByRoomIdAndSendAtLessThanOrderBySendAtDesc(String roomId, LocalDateTime centerTime, PageRequest of);

    List<OpenChatMessage> findTopByRoomIdAndSendAtGreaterThanEqualOrderBySendAtAsc(String roomId, LocalDateTime centerTime, PageRequest of);
}
