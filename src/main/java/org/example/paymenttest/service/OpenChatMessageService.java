package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.request.OpenChatMessageRequest;
import org.example.paymenttest.entity.OpenChatParticipant;
import org.example.paymenttest.entity.chat.OpenChatMessage;
import org.example.paymenttest.entity.chat.OpenChatRoom;
import org.example.paymenttest.repository.chat.OpenChatMessageRepository;
import org.example.paymenttest.repository.chat.OpenChatParticipantRepository;
import org.example.paymenttest.repository.chat.OpenChatRoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenChatMessageService {

    private final OpenChatMessageRepository openChatMessageRepository;
    private final OpenChatRoomRepository openChatRoomRepository;
    private final OpenChatParticipantRepository openChatParticipantRepository;
    public boolean processMessage(OpenChatMessageRequest message, String sender){

        message.setSender(sender);
        message.setSendAt(LocalDateTime.now());

        if("TALK".equals(message.getType())){
            OpenChatMessage openChatMessage = new OpenChatMessage(
                    null,
                    message.getRoomId(),
                    sender,
                    message.getContent(),
                    message.getSendAt()
            );
            openChatMessageRepository.save(openChatMessage);
            return true;
        }

        if("ENTER".equals(message.getType())){
            Optional<OpenChatParticipant> existing = openChatParticipantRepository.findByRoomIdAndUsername(message.getRoomId(), message.getSender());

            if(existing.isPresent()){
                return false;
            }

            OpenChatParticipant participant = new OpenChatParticipant(
                    null,
                    message.getRoomId(),
                    sender,
                    LocalDateTime.now()
            );
            openChatParticipantRepository.save(participant);
            increaseParticipant(message.getRoomId());
            return true;
        }
        return false;
    }

    public void increaseParticipant(String roomId){
        OpenChatRoom room = openChatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("채팅방이 존재하지 않습니다."));
        room.setParticipantCount(room.getParticipantCount() + 1);
        openChatRoomRepository.save(room);
    }

    public List<OpenChatMessage> getMessagesSince(String roomId, LocalDateTime since) {
        return openChatMessageRepository.findByRoomIdAndSendAtAfter(roomId, since);
    }
}
