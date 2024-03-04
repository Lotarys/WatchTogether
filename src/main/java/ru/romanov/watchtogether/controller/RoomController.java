package ru.romanov.watchtogether.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ru.romanov.watchtogether.model.*;
import ru.romanov.watchtogether.service.RoomService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


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
    @SendTo("/topic/room/{roomId}/leave")
    public String leave(@DestinationVariable String roomId, @RequestParam String username) {
        return roomService.leaveUser(roomId, username);
    }

    @PostMapping("/room/{roomId}/join")
    public ResponseEntity<?> joinRoom(@RequestParam String username, @PathVariable String roomId) throws ExecutionException, InterruptedException {
        Room room = roomService.joinRoom(username, roomId);
        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/synchronization", roomId);
        CompletableFuture<PlayerState> future = roomService.getFuture(roomId);
        PlayerState playerState = future.get();
        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/join", new User(username));
        return ResponseEntity.status(HttpStatus.OK).body(new RoomWithPlayerState(room, playerState));
    }

    @MessageMapping("/{roomId}/synchronizationResponse")
    public void handleSynchronizationResponse(@DestinationVariable String roomId, PlayerState playerState) {
        roomService.completeFuture(roomId, playerState);
    }

    @MessageMapping("/room/{roomId}/playlist/add")
    @SendTo("/topic/room/{roomId}/playlist/add")
    public Video addVideo(@DestinationVariable String roomId, @RequestBody Video video) {
        roomService.addVideo(roomId, video);
        return video;
    }

    @MessageMapping("/room/{roomId}/playlist/remove")
    @SendTo("/topic/room/{roomId}/playlist/remove")
    public Video removeVideo(@DestinationVariable String roomId, @RequestBody Video video) {
        roomService.removeVideo(roomId, video);
        return video;
    }

    @MessageMapping("/room/{roomId}/playlist/update")
    @SendTo("/topic/room/{roomId}/playlist/update")
    public List<Video> updateVideo(@DestinationVariable String roomId, @RequestBody List<Video> videos) {
        roomService.updateVideo(roomId, videos);
        return videos;
    }
}
