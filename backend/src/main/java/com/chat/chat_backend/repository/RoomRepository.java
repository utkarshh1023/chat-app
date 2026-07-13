package com.chat.chat_backend.repository;

import com.chat.chat_backend.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room,String> {

    Room findByRoomId(String roomId);
}
