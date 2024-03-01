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
import ru.romanov.watchtogether.model.Video;
import ru.romanov.watchtogether.service.RoomService;
import java.util.List;

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

    @MessageMapping("/room/{roomId}/video/add")
    @SendTo("/topic/{roomId}/video/add")
    public Video addVideo(@DestinationVariable String roomId, @RequestBody Video video) {
        roomService.addVideo(roomId, video);
        return video;
    }

    @MessageMapping("/room/{roomId}/video/remove")
    @SendTo("/topic/{roomId}/video/remove")
    public Video removeVideo(@DestinationVariable String roomId, @RequestBody Video video) {
        roomService.removeVideo(roomId, video);
        return video;
    }

    @MessageMapping("/room/{roomId}/video/update")
    @SendTo("/topic/{roomId}/video/update")
    public List<Video> updateVideo(@DestinationVariable String roomId, @RequestBody List<Video> videos) {
        roomService.updateVideo(roomId, videos);
        return videos;
    }
}
