package org.example.paymenttest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "chat_rooms")
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
    private String roomId;

    private String userName;

    private String lastMessage;

    private LocalDateTime lastTimestamp;

    private boolean hasUnread;

}
