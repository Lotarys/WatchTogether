package ru.romanov.watchtogether.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanov.watchtogether.exception.RoomNotFoundException;
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
    public Room createRoom(String username) {
        User hostUser = new User(username, true);
        String roomId = UUID.randomUUID().toString();
        Room room = new Room(roomId, hostUser, username);
        redisTemplate.opsForValue().set(roomId, room);
        return room;
    }

    @Transactional
    public Room join(String username, String roomId) {
        return addUser(roomId, username);
    }

    public Room getRoom(String roomId) {
        Room room = redisTemplate.opsForValue().get(roomId);
        if (room == null) {
            throw new RoomNotFoundException("The room was not found");
        }
        return room;
    }

    private Room addUser(String roomId, String username) {
        Room room = getRoom(roomId);
        room.getUsers().
                stream().
                forEach(e -> {
            if(e.getUsername().equals(username)) {
                throw new UsernameUniqueException("The username is already occupied");
            }
        });
        room.addUser(new User(username));
        redisTemplate.opsForValue().set(roomId, room);
        return room;
    }

    @Transactional
    public void removeUser(String roomId, String username) {
        Room room = getRoom(roomId);
        room.removeUser(username);
        if(username == room.getHostUsername() || room.getUsers().isEmpty()) {
            redisTemplate.delete(roomId);
        } else {
            redisTemplate.opsForValue().set(roomId, room);
        }
    }

    @Transactional
    public void addVideoLink(String roomId, String videoLink) {
        Room room = getRoom(roomId);
        room.addVideoLink(videoLink);
        redisTemplate.opsForValue().set(roomId, room);
    }
}
