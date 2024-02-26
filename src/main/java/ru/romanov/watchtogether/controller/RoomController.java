package ru.romanov.watchtogether.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ru.romanov.watchtogether.model.Room;
import ru.romanov.watchtogether.model.User;
import ru.romanov.watchtogether.service.RoomService;

@RestController
public class RoomController {

    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    public RoomController(RoomService roomService, SimpMessagingTemplate messagingTemplate) {
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/room/create")
    public ResponseEntity<?> createRoom(@RequestParam String username) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(username));
    }

    @MessageMapping("/room/{roomId}/leave")
    @SendTo("/topic/{roomId}/leave")
    public String leave(@DestinationVariable String roomId, @RequestParam String username) {
        return roomService.leaveUser(roomId, username);
    }

    @PostMapping("/room/{roomId}/join")
    public ResponseEntity<?> joinRoom(@RequestParam String username, @PathVariable String roomId) {
        Room room = roomService.joinRoom(username, roomId);
        messagingTemplate.convertAndSend("/topic/" + roomId + "/join", new User(username));
        return ResponseEntity.status(HttpStatus.OK).body(room);
    }

    @MessageMapping("/room/{roomId}/addVideo")
    @SendTo("/topic/{roomId}/addVideo")
    public String addVideo(@DestinationVariable String roomId, @RequestParam String url) {
        roomService.addVideo(roomId, url);
        return url;
    }

    @MessageMapping("/room/{roomId}/removeVideo")
    @SendTo("/topic/{roomId}/removeVideo")
    public String removeVideo(@DestinationVariable String roomId, @RequestParam String url) {
        roomService.removeVideo(roomId, url);
        return url;
    }
}
