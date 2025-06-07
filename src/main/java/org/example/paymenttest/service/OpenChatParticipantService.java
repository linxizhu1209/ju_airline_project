package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.OpenChatParticipant;
import org.example.paymenttest.repository.chat.OpenChatParticipantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OpenChatParticipantService {

    private final OpenChatParticipantRepository repository;

    public ResponseEntity<OpenChatParticipant> findByRoomIdAndUsername(String roomId, String username) {
        return repository.findByRoomIdAndUsername(roomId, username)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    public void addParticipant(String roomId, String username){
        OpenChatParticipant participant = new OpenChatParticipant(null, roomId, username, LocalDateTime.now());
        repository.save(participant);
    }
}
