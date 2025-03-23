package org.example.paymenttest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.paymenttest.config.RabbitMQConfig;
import org.example.paymenttest.entity.ChatMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(ChatMessage message){
        try{
            String jsonMessage = objectMapper.writeValueAsString(message);

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CHAT_EXCHANGE,
                    RabbitMQConfig.CHAT_ROUTING_KEY,
                    jsonMessage
            );
            System.out.println("메시지 발송 완료!"+ message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
