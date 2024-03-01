package ru.romanov.watchtogether.model;

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
}
