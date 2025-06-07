package org.example.paymenttest.repository.chat;

import org.example.paymenttest.entity.OpenChatParticipant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OpenChatParticipantRepository extends MongoRepository<OpenChatParticipant, String> {
    Optional<OpenChatParticipant> findByRoomIdAndUsername(String roomId, String username);
}
