package ru.romanov.watchtogether.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.romanov.watchtogether.model.ChatMessage;
import ru.romanov.watchtogether.model.PlayerState;

@Controller
public class VideoController {

    @MessageMapping("/video/{roomId}/pause")
    @SendTo("/topic/{roomId}/pause")
    public PlayerState pause(@Payload PlayerState playerState) {
        return playerState;
    }

    @MessageMapping("/video/{roomId}/chat")
    @SendTo("/topic/{roomId}/chat")
    public ChatMessage chat(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/video/{roomId}/resume")
    @SendTo("/topic/{roomId}/resume")
    public PlayerState resume(@Payload PlayerState playerState) {
        return playerState;
    }
}
