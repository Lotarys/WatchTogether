package ru.romanov.watchtogether.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {
    private String id;
    private String hostUsername;

    private List<String> users = new ArrayList<>();

    private Queue<String> videoLinks;

    public Room() {
    }

    public Room(String id, String hostUsername) {
        this.id = id;
        this.hostUsername = hostUsername;
        users.add(hostUsername);
        this.videoLinks = new LinkedList<>();
    }

    public void addUser(String username) {
        users.add(username);
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public void addVideoLink(String videoLink) {
        videoLinks.add(videoLink);
    }

    public String getNextVideoLink() {
        return videoLinks.poll();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostUsername() {
        return hostUsername;
    }

    public void setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }

    public List<String> getUsers() {
        return users;
    }

    public Queue<String> getVideoLinks() {
        return videoLinks;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", hostUsername='" + hostUsername + '\'' +
                ", users=" + users +
                ", videoLinks=" + videoLinks +
                '}';
    }
}
