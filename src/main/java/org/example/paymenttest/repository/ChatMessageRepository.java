package org.example.paymenttest.repository;

import org.example.paymenttest.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findBySender(String sender);
}
