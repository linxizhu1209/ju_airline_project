package org.example.paymenttest.repository;

import org.example.paymenttest.entity.chat.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    int countByHasUnreadIsTrue();
}
