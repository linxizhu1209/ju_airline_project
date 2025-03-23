package org.example.paymenttest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.paymenttest.config.RabbitMQConfig;
import org.example.paymenttest.entity.ChatMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.CHAT_QUEUE)
    public void receiveMessage(String messageJson){
        try{
            ChatMessage message = objectMapper.readValue(messageJson, ChatMessage.class);
            System.out.println("메시지 수신" + message);
        } catch (JsonProcessingException e){
            System.err.println("메시지 역직렬화 오류 " + e.getMessage());
        }
    }

}
