package ru.romanov.watchtogether.model;

public class PlayerState {

    private Video currentVideo;
    private long currentTime;
    private long seekTime;
    private boolean isPlaying;

    public PlayerState() {
    }

    public PlayerState(Video currentVideo, long currentTime, long seekTime, boolean isPlaying) {
        this.currentVideo = currentVideo;
        this.currentTime = currentTime;
        this.seekTime = seekTime;
        this.isPlaying = isPlaying;
    }

    public long getSeekTime() {
        return seekTime;
    }

    public void setSeekTime(long seekTime) {
        this.seekTime = seekTime;
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

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
