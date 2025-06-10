package org.example.paymenttest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.example.paymenttest.config.RabbitMQConfig;
import org.example.paymenttest.entity.chat.ChatMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public void sendMessage(ChatMessage message){
        try{
            String jsonMessage = objectMapper.writeValueAsString(message);

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CHAT_EXCHANGE,
                    RabbitMQConfig.CHAT_ROUTING_KEY,
                    jsonMessage
            );
            messagingTemplate.convertAndSend("/topic/chat.room_" + message.getRoomId(), message);

            System.out.println("메시지 발송 완료!"+ message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
