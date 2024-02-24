package ru.romanov.watchtogether.model;

public class User {

    private String username;
    private boolean isOwner;

    public User(String username) {
        setOwner(false);
        this.username = username;
    }

    public User(String username, boolean isOwner) {
        this.username = username;
        this.isOwner = isOwner;
    }

    public User() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getIsOwner() {
        return isOwner;
    }

    public String getUsername() {
        return username;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }
}
