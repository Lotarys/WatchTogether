package ru.romanov.watchtogether.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanov.watchtogether.exception.RoomNotFoundException;
import ru.romanov.watchtogether.exception.UserNotFoundException;
import ru.romanov.watchtogether.exception.UsernameUniqueException;
import ru.romanov.watchtogether.model.Room;
import ru.romanov.watchtogether.model.User;

import java.util.UUID;

@Service
public class RoomService {

    private final RedisTemplate<String, Room> redisTemplate;

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
            throw new RuntimeException("Failed to create room in Redis: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Room joinRoom(String username, String roomId) {
        return addUser(username, roomId);
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
            throw new RuntimeException("Failed to add user in Redis: " + e.getMessage(), e);
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
        Room room = getRoom(roomId);
        removeUser(room, username);
        if(username.equals(room.getHostUsername()) || room.getUsers().isEmpty()) {
            redisTemplate.delete(roomId);
        } else {
            redisTemplate.opsForValue().set(roomId, room);
        }
        return username;
    }

    @Transactional
    public void addVideoLink(String roomId, String videoLink) {
        Room room = getRoom(roomId);
        room.addVideoLink(videoLink);
        redisTemplate.opsForValue().set(roomId, room);
    }
}
