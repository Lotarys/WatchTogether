package ru.romanov.watchtogether.model;

public class PlayerState {

    private String currentVideo;
    private long currentTime;

    public PlayerState() {
    }

    public PlayerState(String currentVideo, long currentTime) {
        this.currentVideo = currentVideo;
        this.currentTime = currentTime;
    }

    public String getCurrentVideo() {
        return currentVideo;
    }

    public void setCurrentVideo(String currentVideo) {
        this.currentVideo = currentVideo;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
}
