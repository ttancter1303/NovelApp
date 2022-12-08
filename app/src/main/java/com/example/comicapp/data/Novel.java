package com.example.comicapp.data;

import java.util.List;

public class Novel {
    private String id;
    private String title;
    private String intro;
    private String image;
    private String author;
    private Boolean status;
    private List<Chapter> chapters;

    public void setChapters(List<Chapter> chapters){
        this.chapters = chapters;
    }

    public Novel(String id, String title, String image, Boolean status) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.author = author;
        this.status = status;
    }

    public Novel(String id, String title, String intro, String image, String author, Boolean status) {
        this.id = id;
        this.title = title;
        this.intro = intro;
        this.image = image;
        this.author = author;
        this.status = status;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    @Override
    public String toString() {
        return "Novel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", status=" + status +
                ", chapters=" + chapters +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
