package com.chat.chat_backend.controller;

import com.chat.chat_backend.entity.Message;
import com.chat.chat_backend.entity.Room;
import com.chat.chat_backend.payload.MessageRequest;
import com.chat.chat_backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5174")
public class ChatController {



    private final  RoomRepository roomRepository;

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody  MessageRequest request
    ){

      Room room =  roomRepository.findByRoomId(request.getRoomId());

      Message message= new Message();
      message.setContent(request.getContent());
      message.setSender(request.getSender());
      message.setTimeStamp(LocalDateTime.now());

      if(room != null){
          room.getMessage().add(message);
          roomRepository.save(room);
      }else{
          throw new RuntimeException("rooms not found");
      }
        return message;


    }

}
