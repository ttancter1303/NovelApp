package com.example.comicapp.data;

public class User {
    private String id;
    private String name;
    private String email;
    private int birth;
    private int phoneNumb;
    private String introduce;

    public User(String id, String name, String email, int birth, int phoneNumb, String introduce) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.phoneNumb = phoneNumb;
        this.introduce = introduce;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBirth() {
        return birth;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public int getPhoneNumb() {
        return phoneNumb;
    }

    public void setPhoneNumb(int phoneNumb) {
        this.phoneNumb = phoneNumb;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
