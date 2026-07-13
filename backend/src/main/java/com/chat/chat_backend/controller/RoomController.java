package com.chat.chat_backend.controller;

import com.chat.chat_backend.entity.Message;
import com.chat.chat_backend.entity.Room;
import com.chat.chat_backend.repository.RoomRepository;
import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5174")
public class RoomController {

    private final RoomRepository roomRepository;

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody  String roomId){
        if(roomRepository.findByRoomId(roomId) != null){
            return ResponseEntity.badRequest().body("room already exist");

        }
        Room room = new Room();
        room.setRoomId(roomId);
       Room savedRoom =  roomRepository.save(room);

       return ResponseEntity.status(HttpStatus.CREATED).body(room);

    }
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId){

      Room room =   roomRepository.findByRoomId(roomId);

      if(room == null){
          return ResponseEntity.badRequest().body("room not found");

      }
        return ResponseEntity.ok(room);


    }

    @GetMapping("/{roomId}/messages")

    public ResponseEntity<List<Message>> getMessages(@PathVariable String roomId,
                                                     @RequestParam(value = "page",defaultValue = "0",required = false)int page,
                                                     @RequestParam(value = "size",defaultValue = "20",required = false)int size){

      Room room =   roomRepository.findByRoomId(roomId);

      if(room == null){
          return ResponseEntity.badRequest().build();
      }
        List<Message> messages  = room.getMessage();
      int start = Math.max(0,messages.size() - (page + 1) * size);
      int end = Math.min(messages.size(), start + size);
      List<Message> paginatedMessages  =messages.subList(start,end);

      return ResponseEntity.ok(messages);




    }





}
