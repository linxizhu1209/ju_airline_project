package org.example.paymenttest.repository.chat;

import org.example.paymenttest.entity.chat.OpenChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OpenChatRoomRepository extends MongoRepository<OpenChatRoom, String> {
    List<OpenChatRoom> findByDestination(String destination);
    List<OpenChatRoom> findTop2ByDestinationOrderByLastTimestampDesc(String destination);

}
