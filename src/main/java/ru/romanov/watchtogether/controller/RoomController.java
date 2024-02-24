package ru.romanov.watchtogether.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/room/join")
    public ResponseEntity<?> joinRoom(@RequestParam String username, @RequestParam String roomId) {
        Room room = roomService.join(username, roomId);
        messagingTemplate.convertAndSend("/topic/" + roomId + "/join", new User(username));
        return ResponseEntity.status(HttpStatus.OK).body(room);
    }
}
