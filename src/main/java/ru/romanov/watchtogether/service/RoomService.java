package ru.romanov.watchtogether.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanov.watchtogether.exception.*;
import ru.romanov.watchtogether.model.PlayerState;
import ru.romanov.watchtogether.model.Room;
import ru.romanov.watchtogether.model.User;
import ru.romanov.watchtogether.model.Video;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomService {

    private final RedisTemplate<String, Room> redisTemplate;
    private final Map<String, CompletableFuture<PlayerState>> futures = new ConcurrentHashMap<>();
    public RoomService(RedisTemplate<String, Room> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public String createRoom(String username) {
        try {
            User hostUser = new User(username, true);
            String roomId = UUID.randomUUID().toString();
            Room room = new Room(roomId, hostUser, username);
            redisTemplate.opsForValue().set(roomId, room);
            return roomId;
        } catch (Exception e) {
            throw new CreateRoomException("Failed to create room in Redis: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Room joinRoom(String username, String roomId) {
        Room room = addUser(username, roomId);
        CompletableFuture<PlayerState> future = new CompletableFuture<>();
        futures.put(roomId, future);
        return room;
    }

    public CompletableFuture<PlayerState> getFuture(String roomId) {
        return futures.get(roomId);
    }

    public void completeFuture(String roomId, PlayerState playerState) {
        CompletableFuture<PlayerState> future = futures.remove(roomId);
        if (future != null) {
            future.complete(playerState);
        }
    }

    public Room getRoom(String roomId) {
        Room room = redisTemplate.opsForValue().get(roomId);
        if (room == null) {
            throw new RoomNotFoundException("The room was not found");
        }
        return room;
    }

    private Room addUser(String username, String roomId) {
        try {
            Room room = getRoom(roomId);
            if (room.getUsers().stream().anyMatch(e -> e.getUsername().equals(username))) {
                throw new UsernameUniqueException("The username is already occupied");
            }
            room.addUser(new User(username));
            redisTemplate.opsForValue().set(roomId, room);
            return room;
        } catch (Exception e) {
            throw new UserOperationException("Failed to add user in Redis: " + e.getMessage(), e);
        }
    }

    private void removeUser(Room room, String username) {
        boolean userExists = room.getUsers().removeIf(user -> user.getUsername().equals(username));
        if (!userExists) {
            throw new UserNotFoundException("User " + username + " not found!");
        }
    }

    @Transactional
    public String leaveUser(String roomId, String username) {
        try {
            Room room = getRoom(roomId);
            removeUser(room, username);
            if (room.getUsers().isEmpty()) {
                redisTemplate.delete(roomId);
            } else {
                redisTemplate.opsForValue().set(roomId, room);
            }
            return username;
        } catch (Exception e) {
            throw new UserOperationException("Failed to delete a user: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void addVideo(String roomId, Video video) {
        try {
            Room room = getRoom(roomId);
            room.getVideos().add(video);
            redisTemplate.opsForValue().set(roomId, room);
        } catch (Exception e) {
            throw new PlaylistException("Failed add video: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void removeVideo(String roomId, Video video) {
        try {
            Room room = getRoom(roomId);
            room.getVideos().remove(video);
            redisTemplate.opsForValue().set(roomId, room);
        } catch (Exception e) {
            throw new PlaylistException("Failed remove video: " + e.getMessage(), e);
        }
    }

    public void updateVideo(String roomId, List<Video> videos) {
        try {
            Room room = getRoom(roomId);
            room.setVideos(videos);
            redisTemplate.opsForValue().set(roomId, room);
        } catch (Exception e) {
            throw new PlaylistException("Failed update playlist: " + e.getMessage(), e);
        }
    }
}
