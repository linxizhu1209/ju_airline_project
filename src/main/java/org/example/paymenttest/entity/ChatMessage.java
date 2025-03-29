package org.example.paymenttest.entity;

import com.fasterxml.jackson.databind.DatabindException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(collection = "chat_messages")
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    @Id
    private String id;
    private String sender;
    private String message;
    private LocalDateTime timestamp;
}
