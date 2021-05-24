package com.example.restapiproject.Models;

public class Data {
    private String title,message;
    private String image;

    public Data() {
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
}
