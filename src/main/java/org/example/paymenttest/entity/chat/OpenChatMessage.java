package org.example.paymenttest.entity.chat;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "open_chat_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenChatMessage {

    @Id
    private String id;
    private String roomId;
    private String sender;
    private String content;
    private LocalDateTime sendAt;
}
