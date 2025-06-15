package org.example.paymenttest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadMessageRequest {
    private String roomId;
    private String messageId;
    private String username;
}
