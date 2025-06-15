package org.example.paymenttest.entity.chat;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "open_chat_rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenChatRoom {
    @Id
    private String id;
    private String destination;
    private String roomName;
    private String imageUrl;
    private String lastMessage;
    private LocalDateTime lastTimestamp;
    private Long participantCount;
}
