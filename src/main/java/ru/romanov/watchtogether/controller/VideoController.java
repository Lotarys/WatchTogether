package ru.romanov.watchtogether.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.romanov.watchtogether.model.PlayerState;

@Controller
public class VideoController {

    @MessageMapping("/video/{roomId}/pause")
    @SendTo("/topic/video/{roomId}/pause")
    public PlayerState pause(@Payload PlayerState playerState) {
        return playerState;
    }

    @MessageMapping("/video/{roomId}/resume")
    @SendTo("/topic/video/{roomId}/resume")
    public PlayerState resume(@Payload PlayerState playerState) {
        return playerState;
    }
}
