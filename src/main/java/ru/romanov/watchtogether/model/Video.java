package ru.romanov.watchtogether.model;

import java.util.Objects;

public class Video {

    private String url;
    private String title;
    private String img;

    public Video() {
    }

    public Video(String url, String title, String img) {
        this.url = url;
        this.title = title;
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(url, video.url) && Objects.equals(title, video.title) && Objects.equals(img, video.img);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, title, img);
    }
}
