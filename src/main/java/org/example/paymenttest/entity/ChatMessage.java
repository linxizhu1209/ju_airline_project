package org.example.paymenttest.entity;

import com.fasterxml.jackson.databind.DatabindException;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String sender;

    private String message;

    private String timestamp;
}
