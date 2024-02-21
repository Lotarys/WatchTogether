package ru.romanov.watchtogether.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.romanov.watchtogether.model.ChatMessage;

@Controller
public class VideoController {

    @MessageMapping("/video/{roomId}/pause")
    @SendTo("/topic/{roomId}")
    public String pause() {
        System.out.println("pause");
        return "pause";
    }

    @MessageMapping("/video/{roomId}/chat")
    @SendTo("/topic/{roomId}/chat")
    public ChatMessage chat(@Payload ChatMessage chatMessage) {
        System.out.println(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/video/{roomId}/resume")
    @SendTo("/topic/{roomId}")
    public String resume() {
        return "resume";
    }
}
