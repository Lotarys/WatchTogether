package ru.romanov.watchtogether.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.romanov.watchtogether.model.Room;
import ru.romanov.watchtogether.service.RoomService;

@RestController
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/room/create")
    public ResponseEntity<?> createRoom(@RequestParam String username) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(username));
    }
}
