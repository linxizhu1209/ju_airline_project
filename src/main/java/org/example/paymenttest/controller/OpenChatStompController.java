package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.config.JwtUtil;
import org.example.paymenttest.dto.request.OpenChatMessageRequest;
import org.example.paymenttest.entity.chat.ChatMessage;
import org.example.paymenttest.entity.chat.OpenChatMessage;
import org.example.paymenttest.service.OpenChatMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OpenChatStompController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final JwtUtil jwtUtil;
    private final OpenChatMessageService service;
    private final OpenChatMessageService openChatMessageService;

    @MessageMapping("/open-chat.sendMessage")
    public void handleMessage(OpenChatMessageRequest message) throws AccessDeniedException {
        String token = message.getToken();
        if(token == null || token.isEmpty()){
            throw new AccessDeniedException("토큰이 존재하지않습니다.");
        }
        Authentication auth = jwtUtil.getAuthentication(token);
        boolean shouldBroadcast = service.processMessage(message,message.getSender());

        if(shouldBroadcast) {
            messagingTemplate.convertAndSend("/topic/chat.room." + message.getRoomId(), message);
        }
    }

    @MessageMapping("/open-chat.readMessage")
    public void readMessage(OpenChatMessageRequest message, Principal principal){
        String reader = message.getSender();
        openChatMessageService.markMessageAsRead(message.getRoomId(), message.getId(), reader);

        messagingTemplate.convertAndSend("/topic/chat.room."+message.getRoomId(),
                Map.of(
                        "type", "READ",
                        "roomId", message.getRoomId(),
                        "messageId", message.getId(),
                        "username", reader
                ));
    }


}
