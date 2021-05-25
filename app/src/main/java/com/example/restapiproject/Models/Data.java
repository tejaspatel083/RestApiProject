package com.example.restapiproject.Models;

public class Data {
    private String id,title,message,email;
    private String image;

    public Data() {
    }

    public Data(String id, String title, String message, String email, String image) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.email = email;
        this.image = image;
    }

    public Data(String id, String title, String message, String image) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.image = image;
    }

    public Data(String title, String message, String image) {
        this.title = title;
        this.message = message;
        this.image = image;
    }

    public Data(String title, String image) {
        this.title = title;
        this.image = image;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
