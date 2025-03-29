package org.example.paymenttest.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.response.ChatRoomResponse;
import org.example.paymenttest.entity.ChatMessage;
import org.example.paymenttest.service.ChatMessageProducer;
import org.example.paymenttest.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageProducer chatMessageProducer;
    private final ChatMessageService chatMessageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody ChatMessage chatMessage) {
        chatMessageProducer.sendMessage(chatMessage);
        chatMessageService.saveMessage(chatMessage);
        return ResponseEntity.ok("Message sent successfully");
    }


    @GetMapping("/admin/messages/{sender}")
    public ResponseEntity<List<ChatMessage>> getMessagesBySender(@PathVariable String sender) {
        List<ChatMessage> messages = chatMessageService.getMessagesBySender(sender);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/admin/messages")
    public ResponseEntity<List<ChatMessage>> getAllMessages() {
        List<ChatMessage> messages = chatMessageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/admin/chatrooms")
    public ResponseEntity<List<ChatRoomResponse>> getChatRooms(){
        List<ChatRoomResponse> chatRooms = chatMessageService.getChatRooms();
        return ResponseEntity.ok(chatRooms);
    }


}
