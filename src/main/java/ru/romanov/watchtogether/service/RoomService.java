package ru.romanov.watchtogether.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.romanov.watchtogether.exception.RoomNotFoundException;
import ru.romanov.watchtogether.exception.UsernameUniqueException;
import ru.romanov.watchtogether.model.Room;

import java.util.UUID;

@Service
public class RoomService {

    private final RedisTemplate<String, Room> redisTemplate;

    public RoomService(RedisTemplate<String, Room> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String createRoom(String hostUsername) {
        String roomId = UUID.randomUUID().toString();
        Room room = new Room(roomId, hostUsername);
        room.setHostUsername(hostUsername);
        redisTemplate.opsForValue().set(roomId, room);
        return roomId;
    }

    public void join(String username, String roomId) {
        addUser(roomId, username);
    }

    public Room getRoom(String roomId) {
        Room room = redisTemplate.opsForValue().get(roomId);
        if (room == null) {
            throw new RoomNotFoundException("The room was not found");
        }
        return room;
    }

    private void addUser(String roomId, String username) {
        Room room = getRoom(roomId);
        if(room.getUsers().contains(username)) {
            throw new UsernameUniqueException("The username is already occupied");
        }
        room.addUser(username);
        redisTemplate.opsForValue().set(roomId, room);
    }

    public void removeUser(String roomId, String username) {
        Room room = getRoom(roomId);
        room.removeUser(username);
        if(username == room.getHostUsername()) {
            redisTemplate.delete(roomId);
        }
        if (room.getUsers().isEmpty()) {
            redisTemplate.delete(roomId);
        }
        redisTemplate.opsForValue().set(roomId, room);
    }

    public void addVideoLink(String roomId, String videoLink) {
        Room room = getRoom(roomId);
        room.addVideoLink(videoLink);
        redisTemplate.opsForValue().set(roomId, room);
    }

    public String getNextVideoLink(String roomId) {
        Room room = getRoom(roomId);
        return room.getNextVideoLink();
    }
}
