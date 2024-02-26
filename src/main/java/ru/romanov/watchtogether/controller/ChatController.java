package ru.romanov.watchtogether.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.romanov.watchtogether.model.ChatMessage;

@Controller
public class ChatController {

    @MessageMapping("/video/{roomId}/chat")
    @SendTo("/topic/{roomId}/chat")
    public ChatMessage chat(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
