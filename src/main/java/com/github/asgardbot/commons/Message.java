package com.github.asgardbot.commons;

public class Message {

    private String payload;
    private User user;

    public Message(User user, String payload) {
        this.user = user;
    }

    public String getPayload() {
        return payload;
    }

    public User getUser() {
        return user;
    }
}
