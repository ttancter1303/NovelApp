package com.example.comicapp.data;

public class Comic {
    private String id;
    private String title;
    private String image;
    private String author;
    private Boolean status;

    public Comic(String id, String title, String image, Boolean status) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.author = author;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", status=" + status +
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
