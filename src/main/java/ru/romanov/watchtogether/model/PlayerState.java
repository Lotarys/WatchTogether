package ru.romanov.watchtogether.model;

public class PlayerState {

    private Video currentVideo;
    private long currentTime;

    public PlayerState() {
    }


    public PlayerState(Video currentVideo, long currentTime) {
        this.currentVideo = currentVideo;
        this.currentTime = currentTime;
    }

    public Video getCurrentVideo() {
        return currentVideo;
    }

    public void setCurrentVideo(Video currentVideo) {
        this.currentVideo = currentVideo;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
}
