package ru.romanov.watchtogether.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.romanov.watchtogether.model.ChatMessage;

@Controller
public class ChatController {

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage chat(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
