package com.example.comicapp.data;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class Novel {
    private String id;
    private String name;
    private String intro;
    private String image;
    private String type;
    private String date;
    private DocumentReference author;
    private Boolean status;
    private List<Chapter> chapters;

    public Novel(String id, String name, String intro, String image, String type, String date, DocumentReference author, Boolean status, List<Chapter> chapters) {
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.image = image;
        this.type = type;
        this.date = date;
        this.author = author;
        this.status = status;
        this.chapters = chapters;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Novel(String id, String name, String intro, String image, String type, DocumentReference author, Boolean status, List<Chapter> chapters) {
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.image = image;
        this.type = type;
        this.author = author;
        this.status = status;
        this.chapters = chapters;
    }

    public Novel(String id, String name, String intro, String image, String type, DocumentReference author, Boolean status) {
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.image = image;
        this.type = type;
        this.author = author;
        this.status = status;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setChapters(List<Chapter> chapters){
        this.chapters = chapters;
    }

    public Novel() {
    }

    public Novel(String id, String name, String image, Boolean status) {
        this.id = id;
        this.name = name;
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

    public Novel(String id, String name, String intro, String image, DocumentReference author, Boolean status, List<Chapter> chapters) {
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.image = image;
        this.author = author;
        this.status = status;
        this.chapters = chapters;
    }

    @Override
    public String toString() {
        return "Novel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
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

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DocumentReference getAuthor() {
        return author;
    }

    public void setAuthor(DocumentReference author) {
        this.author = author;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
