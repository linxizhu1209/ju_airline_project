package org.example.paymenttest.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "open_chat_participants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenChatParticipant {

    @Id
    private String id;
    private String roomId;
    private String username;
    private LocalDateTime joinedAt;
    private String lastReadMessageId;

}
