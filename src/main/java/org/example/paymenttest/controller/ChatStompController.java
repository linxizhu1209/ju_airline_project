package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.config.JwtUtil;
import org.example.paymenttest.entity.ChatMessage;
import org.example.paymenttest.service.ChatMessageProducer;
import org.example.paymenttest.service.ChatMessageService;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class ChatStompController {
    private final ChatMessageProducer producer;
    private final ChatMessageService chatMessageService;
    private final JwtUtil jwtUtil;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage message) throws AccessDeniedException {
        String token = message.getToken();
        if(token == null || token.isEmpty()) {
            throw new AccessDeniedException("토큰이 없습니다");
        }
        Authentication auth = jwtUtil.getAuthentication(token);
        String sender = auth.getName();
        String role = auth.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("ROLE_USER");
        message.setSender(sender);
        chatMessageService.saveMessage(message, role);
        producer.sendMessage(message);
    }
}
