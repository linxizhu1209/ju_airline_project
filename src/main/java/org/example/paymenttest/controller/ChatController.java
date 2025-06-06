package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.config.JwtUtil;
import org.example.paymenttest.dto.response.ChatRoomResponse;
import org.example.paymenttest.entity.chat.ChatMessage;
import org.example.paymenttest.service.ChatMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final JwtUtil jwtUtil;


    @GetMapping("/admin/chatrooms")
    public ResponseEntity<List<ChatRoomResponse>> getChatRooms(){
        List<ChatRoomResponse> chatRooms = chatMessageService.getChatRooms();
        return ResponseEntity.ok(chatRooms);
    }


    @GetMapping("/messages/{roomId}")
    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable String roomId, Authentication authentication) {
        String role = authentication.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("ROLE_USER");
        String requester = authentication.getName();

        List<ChatMessage> chatMessages = chatMessageService.markMessagesAsRead(roomId, role, requester);
        return ResponseEntity.ok(chatMessages);
    }

    @GetMapping("/room/exists/{roomId}")
    public ResponseEntity<Map<String, Boolean>> roomExists(@PathVariable String roomId) {
        boolean exists = chatMessageService.existsByRoomId(roomId);
        return ResponseEntity.ok(Map.of("exists", exists));
    }


    @GetMapping("/unread/count")
    public ResponseEntity<Map<String, Integer>> getUnreadCount(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(required=false) String roomId){
        if(authHeader == null || !authHeader.startsWith("Bearer ") ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        /**
         * 1. 관리자는 읽지않은 채팅방의 수 => 채팅방에 unread 필드 필요
         * 2. 유저는 읽지않은 채팅의 수 => 채팅에 unread 필드 필요
         */
        System.out.println("📥 [AUTH HEADER] = " + authHeader);
        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        String role = jwtUtil.getRoleFromToken(token);

        int count = chatMessageService.getUnreadCountByEmailAndRole(email, role, roomId);

        return ResponseEntity.ok(Map.of("count", count));
    }

}
