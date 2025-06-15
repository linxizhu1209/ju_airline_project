package org.example.paymenttest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenChatMessageRequest {
    private String id;
    private String roomId;
    private String sender;
    private String content;
    private String type; // "TALK", "ENTER" 등
    private LocalDateTime sendAt;
    private String token;

}
