package org.example.paymenttest.controller.chat;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.chat.OpenChatMessage;
import org.example.paymenttest.service.OpenChatMessageService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-chat")
public class OpenChatMessageController {

    private final OpenChatMessageService service;

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<OpenChatMessage>> getMessagesSince(
            @PathVariable String roomId,
            @RequestParam("since") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since
    ){
        List<OpenChatMessage> messages = service.getMessagesSince(roomId, since);
        return ResponseEntity.of(Optional.ofNullable(messages));
    }

}
