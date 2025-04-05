package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.ChatMessage;
import org.example.paymenttest.service.ChatMessageProducer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatStompController {
    private final ChatMessageProducer producer;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage message) {
        producer.sendMessage(message);
    }
}
