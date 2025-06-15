package org.example.paymenttest.entity.chat;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private Long unreadCount;

    private Set<String> readBy = new HashSet<>(); // 읽은 사람 id 목록
}
