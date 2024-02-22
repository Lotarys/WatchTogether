package ru.romanov.watchtogether.model;

public class User {

    private String username;
    private boolean isOwner;

    public User(String username) {
        this.username = username;
    }

    public User(String username, boolean isOwner) {
        this.username = username;
        this.isOwner = isOwner;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }
}
