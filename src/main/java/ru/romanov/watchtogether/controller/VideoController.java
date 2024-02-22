package ru.romanov.watchtogether.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.romanov.watchtogether.model.ChatMessage;
import ru.romanov.watchtogether.model.User;
import ru.romanov.watchtogether.service.RoomService;

@Controller
public class VideoController {

    private final RoomService roomService;

    public VideoController(RoomService roomService) {
        this.roomService = roomService;
    }

    @MessageMapping("/video/{roomId}/pause")
    @SendTo("/topic/{roomId}")
    public String pause() {
        System.out.println("pause");
        return "pause";
    }

    @MessageMapping("/video/{roomId}/join")
    @SendTo("/topic/{roomId}")
    public User joinRoom(@DestinationVariable String roomId, @Payload String username) {
        roomService.join(username, roomId);
        return new User(username);
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
