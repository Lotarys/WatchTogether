package ru.romanov.watchtogether.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.romanov.watchtogether.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {

    private String roomId;
    private List<User> users = new ArrayList<>();
    private List<String> videos = new LinkedList<>();
    private String hostUsername;


    public Room() {
    }

    public Room(String id, User user, String hostUsername) {
        this.roomId = id;
        users.add(user);
        this.hostUsername = hostUsername;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(String username) {
        boolean removed = users.removeIf(user -> user.getUsername().equals(username));
        if (!removed) {
            throw new UserNotFoundException("User " + username + " not found!");
        }
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public void addVideoLink(String videoLink) {
        videos.add(videoLink);
    }

    public List<User> getUsers() {
        return users;
    }

    public String getHostUsername() {
        return hostUsername;
    }

    public void setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + roomId + '\'' +
                ", users=" + users +
                ", videoLinks=" + videos +
                '}';
    }
}
