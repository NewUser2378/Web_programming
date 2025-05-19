package ru.itmo.wp.form;

import ru.itmo.wp.domain.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PostCredential {

    @NotEmpty
    @Size(min = 1, max = 40)
    private String title;


    @Size(min = 2, max = 255)
    private String text;


    private User user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
