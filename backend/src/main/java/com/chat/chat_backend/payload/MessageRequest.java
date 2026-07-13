package com.chat.chat_backend.payload;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MessageRequest {

    private String content;

    private String sender;

    private String roomId;

    private LocalDateTime messageTime;
}
