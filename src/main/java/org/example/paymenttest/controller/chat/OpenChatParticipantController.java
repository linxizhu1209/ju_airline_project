package org.example.paymenttest.controller.chat;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.OpenChatParticipant;
import org.example.paymenttest.service.OpenChatParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-chat")
public class OpenChatParticipantController {

    private final OpenChatParticipantService service;

    @GetMapping("/{roomId}/participant/{username}")
    public ResponseEntity<OpenChatParticipant> getParticipant(
            @PathVariable("roomId") String roomId,
            @PathVariable String username){
        return service.findByRoomIdAndUsername(roomId, username);
    }

}
