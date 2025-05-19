package ru.itmo.web.hw4.model;

public class Post {

    private long user_id;
    private long id;
    private String title;
    private String text;



    public Post(long id, String title, String text, long user_id) {
        this.user_id = user_id;
        this.id = id;
        this.title = title;
        this.text = text;

    }


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getUserId() {
        return user_id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserId(long user_id) {
        this.user_id = user_id;
    }
}
