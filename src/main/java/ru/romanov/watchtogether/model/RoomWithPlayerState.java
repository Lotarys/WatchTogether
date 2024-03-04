package ru.romanov.watchtogether.model;

public class RoomWithPlayerState {

    private Room room;
    private PlayerState playerState;

    public RoomWithPlayerState() {
    }

    public RoomWithPlayerState(Room room, PlayerState playerState) {
        this.room = room;
        this.playerState = playerState;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }
}
