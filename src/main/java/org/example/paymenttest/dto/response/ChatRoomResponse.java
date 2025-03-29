package org.example.paymenttest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponse {

    private String sender;
    private String lastMessage;
    private LocalDateTime lastMessageTime;



}
