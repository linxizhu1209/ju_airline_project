package org.example.paymenttest.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/commerce/chat")
@RequiredArgsConstructor
public class ChatController {

    @GetMapping
    public String chat(){
        return "chat";
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message, @AuthenticationPrincipal OAuth2User user){
        message.setSender(user.getAttribute("email"));
        message.setTime(new Date());
        return message;
    }


}
