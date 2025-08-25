package org.example.paymenttest.controller.chat;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.request.ReadMessageRequest;
import org.example.paymenttest.service.OpenChatMessageService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-chat")
public class OpenChatMessageController {

    private final OpenChatMessageService service;

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Map<String,Object>>> getMessagesSince(
            @PathVariable String roomId,
            @RequestParam("since") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since,
            @RequestParam("username") String username
    ){
        List<Map<String,Object>> messages = service.getMessagesSince(roomId, since, username);
        return ResponseEntity.of(Optional.ofNullable(messages));
    }

    @GetMapping("/{roomId}/messages/window")
    public ResponseEntity<List<Map<String,Object>>> getMessagesWindow(
            @PathVariable String roomId,
            @RequestParam("lastMessageId") String centerMessageId,
            @RequestParam("prev") int prev,
            @RequestParam("next") int next,
            @RequestParam("username") String username
    ){
        List<Map<String, Object>> messages = service.getMessagesWindow(roomId, centerMessageId, prev, next, username);
        return ResponseEntity.ok(messages);
    }


    @PostMapping("/read")
    public ResponseEntity<?> markAsRead(@RequestBody ReadMessageRequest request){
        service.markMessageAsRead(
                request.getRoomId(), request.getMessageId(), request.getUsername()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@RequestParam String roomId, @RequestParam String messageId){
        long count = service.getUnreadCount(roomId, messageId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{roomId}/last-read")
    public ResponseEntity<String> getLastReadMessageId(
            @PathVariable String roomId,
            @RequestParam String username
    ){
        String messageId = service.getLastReadMessageId(roomId, username);
        return ResponseEntity.ok(messageId);
    }


}
