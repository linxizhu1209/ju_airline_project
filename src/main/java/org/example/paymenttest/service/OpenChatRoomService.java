package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.chat.OpenChatRoom;
import org.example.paymenttest.repository.chat.OpenChatRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenChatRoomService {
    private final OpenChatRoomRepository openChatRoomRepository;

    public Map<String, List<OpenChatRoom>> getGroupedRooms() {
        List<OpenChatRoom> all = openChatRoomRepository.findAll();
        return all.stream().collect(Collectors.groupingBy(OpenChatRoom::getDestination));
    }

    public List<OpenChatRoom> getTop2ByDestination(String destination) {
        return openChatRoomRepository.findTop2ByDestinationOrderByLastTimestampDesc(destination);
    }
}
