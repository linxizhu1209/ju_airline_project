package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.chat.OpenChatRoom;
import org.example.paymenttest.service.OpenChatRoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/open-chat")
@RequiredArgsConstructor
public class OpenChatRoomController {

    private final OpenChatRoomService openChatRoomService;


    @GetMapping("/grouped")
    public Map<String, List<OpenChatRoom>> getGroupedRooms() {
        return openChatRoomService.getGroupedRooms();
    }

    @GetMapping("/top2")
    public List<OpenChatRoom> getTop2(@RequestParam String destination){
        return openChatRoomService.getTop2ByDestination(destination);
    }

}
